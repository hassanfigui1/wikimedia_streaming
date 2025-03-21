package app.service.reflect;

import java.lang.reflect.Field;

public class EntityUpdater {
    public static<T> T updateEntity(T existingEntity, T updatedEntity){
        if(existingEntity == null || updatedEntity == null){
            throw new IllegalArgumentException("Entities Cannot be null");
        }
        Class<?>clazz = existingEntity.getClass();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            try{
                Object updatedValue = field.get(updatedEntity);
                if(updatedValue != null){
                    field.set(existingEntity, updatedValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to update entity field: "+e);
            }
        }
        return existingEntity;
    }
}
