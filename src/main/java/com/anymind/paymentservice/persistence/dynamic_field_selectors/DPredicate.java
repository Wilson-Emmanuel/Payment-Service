package com.anymind.paymentservice.persistence.dynamic_field_selectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wilson
 * On 02-01-2023, 14:10
 */
public  class DPredicate<E> {
    private final StringBuilder where = new StringBuilder();
    private final Map<String,Object> parameters = new HashMap<>();
    private DPredicateBuilder<E> predicateBuilder;
    private DLogicalBuilder<E> logicalBuilder;
     private String entityQueryPlaceholder;
     private int parameterNumber = 0;

    private DPredicate(){}

     public static <E> DPredicateBuilder<E> getBuilder(Class<E> entityType){
         DPredicate<E> instance = new DPredicate<>();
         instance.entityQueryPlaceholder = "_"+entityType.getSimpleName()+"_.";
         instance.predicateBuilder = new DPredicateBuilder<>(instance);
         instance.logicalBuilder = new DLogicalBuilder<>(instance);
         return instance.predicateBuilder;
     }
    public String getQueryString(){
        return this.where.toString();
    }
    public Map<String, Object> getParameters(){
        return this.parameters;
    }
    public static class DLogicalBuilder<E> {
         private final DPredicate<E> dPredicate;
         public DLogicalBuilder(DPredicate<E> dPredicate){
             this.dPredicate = dPredicate;
         }
        public DPredicateBuilder<E> or(){
            dPredicate.where.append(" OR");
            return dPredicate.predicateBuilder;
        }
        public DPredicateBuilder<E> and(){
            dPredicate.where.append(" AND");
            return dPredicate.predicateBuilder;
        }
        public DLogicalBuilder<E> and(DPredicate<E> predicate){
            dPredicate.where.append(" AND (")
                    .append(predicate.getQueryString())
                    .append(")");
            dPredicate.parameters.putAll(predicate.getParameters());
            return dPredicate.logicalBuilder;
        }
        public DLogicalBuilder<E> or(DPredicate<E> predicate){
            dPredicate.where.append(" OR (")
                    .append(predicate.getQueryString())
                    .append(")");
            dPredicate.parameters.putAll(predicate.getParameters());
            return dPredicate.logicalBuilder;
        }
        public DPredicate<E> build(){
             return dPredicate;
        }
        public String getQueryString(){
             return dPredicate.getQueryString();
        }
    }

    public static class DPredicateBuilder<E>{
        private final DPredicate<E> dPredicate;
        public DPredicateBuilder(DPredicate<E> dPredicate){
            this.dPredicate = dPredicate;
        }

        /**
         *
         * @param fieldPath this the entity field to be used in predicates. e.g. price, customer.name
         * @param value
         */
        public DLogicalBuilder<E> equal(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath, value, "=");
        }
        public DLogicalBuilder<E> notEqual(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath, value, "<>");
        }
        public DLogicalBuilder<E> greaterThan(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath, value, ">");
        }
        public DLogicalBuilder<E> greaterThanOrEqual(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath, value, ">=");
        }
        public DLogicalBuilder<E> lessThan(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath, value, "<");
        }
        public DLogicalBuilder<E> lessThanOrEqual(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath, value, ">=");
        }
        public DLogicalBuilder<E> isNull(String fieldPath){
            return appendUnaryPredicate(fieldPath, "IS NULL");
        }
        public DLogicalBuilder<E> isNotNull(String fieldPath){
            return appendUnaryPredicate(fieldPath, "IS NOT NULL");
        }
        //Collection comparison
        public DLogicalBuilder<E> isEmpty(String fieldPath){
            return appendUnaryPredicate(fieldPath, "IS EMPTY");
        }
        //Collection comparison
        public DLogicalBuilder<E> isNotEmpty(String fieldPath){
            return appendUnaryPredicate(fieldPath, "IS NOT EMPTY");
        }

        /*
         * wildcards can be used in the value
         */
        public DLogicalBuilder<E> like(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath,value,"LIKE");
        }

        /*
         * wildcards can be used
         */
        public DLogicalBuilder<E> notLike(String fieldPath, Object value){
            return appendBinaryPredicate(fieldPath,value,"NOT LIKE");
        }
        //operators inclusive
        public DLogicalBuilder<E> between(String fieldPath, Object lowerValue, Object upperValue){
            return appendTenaryPredicate(fieldPath, lowerValue, upperValue,"BETWEEN");
        }
        public DLogicalBuilder<E> notBetween(String fieldPath, Object lowerValue, Object upperValue){
            return appendTenaryPredicate(fieldPath, lowerValue, upperValue,"NOT BETWEEN");
        }
        //Collection comparison
        public DLogicalBuilder<E> in(String fieldPath, List<Object> values){
            return appendBinaryPredicate(fieldPath, values, "IN");
        }
        //Collection comparison
        public DLogicalBuilder<E> notIn(String fieldPath, List<Object> values){
            return appendBinaryPredicate(fieldPath, values, "NOT IN");
        }
        //Collection comparison
        public DLogicalBuilder<E> memberOf(String fieldPath, Object value){
            String placeholder = addToParameters(fieldPath,value);
            //placeholder first before field path
            dPredicate.where.append(" ")
                    .append(placeholder)
                    .append(" MEMBER OF ")
                    .append(dPredicate.entityQueryPlaceholder)
                    .append(fieldPath);
            return dPredicate.logicalBuilder;
        }
        //Collection comparison
        public DLogicalBuilder<E> notMemberOf(String fieldPath, Object value){
            String placeholder = addToParameters(fieldPath,value);
            //placeholder first before field path
            dPredicate.where.append(" ")
                    .append(placeholder)
                    .append(" NOT MEMBER OF ")
                    .append(dPredicate.entityQueryPlaceholder)
                    .append(fieldPath);
            return dPredicate.logicalBuilder;
        }
        private DLogicalBuilder<E> appendTenaryPredicate(String fieldPath, Object lowerValue, Object upperValue, String operator){
            String lowerPlaceholder = addToParameters(fieldPath, lowerValue);
            String upperPlaceholder = addToParameters(fieldPath, upperValue);
            dPredicate.where.append(" ")
                    .append(dPredicate.entityQueryPlaceholder)
                    .append(fieldPath).append(" ")
                    .append(operator).append(" ")
                    .append(lowerPlaceholder)
                    .append(" AND ")
                    .append(upperPlaceholder);
            return dPredicate.logicalBuilder;
        }
        private DLogicalBuilder<E> appendBinaryPredicate(String fieldPath, Object value, String operator){
            String placeholder = addToParameters(fieldPath, value);
            dPredicate.where.append(" ")
                    .append(dPredicate.entityQueryPlaceholder)
                    .append(fieldPath)
                    .append(" ")
                    .append(operator)
                    .append(" ")
                    .append(placeholder);
            return dPredicate.logicalBuilder;
        }
        private DLogicalBuilder<E> appendUnaryPredicate(String fieldPath, String operator){
            dPredicate.where.append(" ")
                    .append(dPredicate.entityQueryPlaceholder)
                    .append(fieldPath)
                    .append(" ")
                    .append(operator);
            return dPredicate.logicalBuilder;
        }
        private String addToParameters(String fieldPath, Object value){
            String placeholder = fieldPath.replaceAll("\\.","_")+(dPredicate.parameterNumber++);
            dPredicate.parameters.put(placeholder, value);
            return ":"+placeholder;
        }

    }


}
