package com.anymind.paymentservice.persistence.dynamic_field_selectors;

import com.anymind.paymentservice.web.exceptions.BusinessLogicException;
import com.anymind.paymentservice.web.models.responses.PagedData;
import graphql.schema.SelectedField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 02-01-2023, 13:24
 *
 *  <Q> graphQL type to be fetched
 * <E> database entity type
 */
public class DQueryProcessor<E>  {

    private EntityManager entityManager;
    private List<SelectedField> selectedFields;

    private Class<E> entityName;
    private DPredicate<E> predicate;
    private String entityPlaceholder;

    private int queryIndex=0;
    private final LinkedHashMap<String, Integer> querySelectFieldsIndex= new LinkedHashMap<>();
    public DQueryProcessor(List<SelectedField> selectedFields, EntityManager en, Class<E> entityClass)  {
        this.selectedFields = selectedFields;
        entityManager = en;
        this.entityName = entityClass;
        this.entityPlaceholder = "_"+entityClass.getSimpleName()+"_";
    }
    public void setPredicate(DPredicate<E> predicate){
        this.predicate = predicate;
    }

    public Map<String,Object> fetchOne(){
        TypedQuery<Tuple> typedQuery = execute();
        return extractResults(typedQuery.getSingleResult(),"", this.selectedFields);
    }
    public List<Map<String,Object>> fetchAll(){
        TypedQuery<Tuple> typedQuery = execute();
        return typedQuery.getResultList().stream()
                .map(tuple -> this.extractResults(tuple,"", this.selectedFields))
                .toList();
    }


    public PagedData fetchPage(int pageNumber, int pageSize, String unionTypeName){
        TypedQuery<Tuple> typedQuery = execute();


        typedQuery.setFirstResult((pageNumber) * pageSize);
        typedQuery.setMaxResults(pageSize);
        List<Map<String,Object>> results = typedQuery.getResultList().stream()
                .map(tuple -> this.extractResults(tuple, "", this.selectedFields))
                .peek(stringObjectMap -> stringObjectMap.put("interfaceType",unionTypeName)).toList();


        //Execute count query
        StringBuilder queryCount = new StringBuilder("SELECT ");
        queryCount.append("count(")
                        .append(entityPlaceholder)
                        .append(")");
        queryCount.append(" FROM ").append(entityName.getSimpleName()).append(" ").append(entityPlaceholder);

        if(this.predicate != null){
            queryCount.append(" WHERE ")
                    .append(predicate.getQueryString());
        }
        Query queryTotal = entityManager.createQuery(queryCount.toString());
        long countResult = (long)queryTotal.getSingleResult();
        int pages = (int) Math.round(Math.ceil((double) countResult / pageSize));

        return PagedData.builder()
                .totalPages(pages)
                .currentPage(pageNumber)
                .pageSize(pageSize)
                .data(results)
                .build();
    }

    private TypedQuery<Tuple> execute(){

        setSelectedFieldQueryIndexes(this.selectedFields,"");
        if(querySelectFieldsIndex.isEmpty())throw new BusinessLogicException("You must select at least one field");

        StringBuilder query = new StringBuilder("SELECT ");
        int count = 0;
        for(String key: querySelectFieldsIndex.keySet()){
            if(count > 0)query.append(",");
            query.append(" ").append(key);
            count++;
        }
        query.append(" FROM ").append(entityName.getSimpleName()).append(" ").append(entityPlaceholder);

        if(this.predicate != null){
            query.append(" WHERE ")
                    .append(predicate.getQueryString());
        }

        //add order by, group by , having sections later
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(query.toString(), Tuple.class);
        if(predicate != null){
            for(String placeholder: predicate.getParameters().keySet()){
                typedQuery.setParameter(placeholder, predicate.getParameters().get(placeholder));
            }
        }
       return typedQuery;
    }

    private Map<String,Object> extractResults(Tuple tuple, String parentName, List<SelectedField> set){
        Map<String, Object> map = new HashMap<>();
        for(SelectedField selectedField : set){
            if(selectedField.getName().equals("__typename"))continue;

            String newParent = StringUtils.hasText(parentName)?parentName+".":parentName;
            if(!selectedField.getSelectionSet().getFields().isEmpty()){
                Map<String,Object> subFieldObj = extractResults(tuple,parentName+selectedField.getName(),selectedField.getSelectionSet().getImmediateFields());
                map.put(selectedField.getName(), subFieldObj);
            }else{
                int index = querySelectFieldsIndex.get(entityPlaceholder+"."+newParent+selectedField.getName());
                map.put(selectedField.getName(), tuple.get(index));
            }
        }
        return map;
    }

    private void setSelectedFieldQueryIndexes(List<SelectedField> set, String parentName){
        for(SelectedField selectedField : set){
            if(selectedField.getName().equals("__typename"))continue;

            String newParent = StringUtils.hasText(parentName)?parentName+".":parentName;
            if(!selectedField.getSelectionSet().getFields().isEmpty()){
                setSelectedFieldQueryIndexes(selectedField.getSelectionSet().getImmediateFields(), parentName+selectedField.getName());
            }else{
                querySelectFieldsIndex.put(entityPlaceholder+"."+newParent+selectedField.getName(), this.queryIndex++);
            }
        }
    }


}
