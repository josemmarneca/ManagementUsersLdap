package com.dxnet.ldap.commons.utils;

import com.dxnet.ldap.database.entities.Usex;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UsexUtils {

    public static Usex convertModelToUsex(Usex user) {
        return user;
    }

    public static void setParametersToCreateDefaultUser(Usex usex) {
        usex.setCreatedBy("API");
        usex.setDescription("New Ldap Schema");
        usex.setObjectClass(new String[]{"top", "person", "user"});
        usex.setDn("CN=" + usex.getCn() + ",CN=Users");
    }

    public static void getParametersToUpdateLDAP(Usex usexUpdating, Usex usexDatabase) {
        usexUpdating.setDn(usexDatabase.getDn());
        usexUpdating.setObjectClass(usexDatabase.getObjectClass());
    }

    public static void updateUserWithParameters(Usex parameterToUpdated, Usex usexDatabase) {
        Field[] ldapFields = parameterToUpdated.getClass().getFields();
        for (Field usexField : ldapFields) {
            String fieldName = usexField.getName();
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                Method getMethod = parameterToUpdated.getClass().getMethod(getMethodName);
                if (getMethod.invoke(parameterToUpdated) != null) {
                    Method setMethod = usexDatabase.getClass().getMethod(setMethodName, usexField.getType());
                    setMethod.invoke(usexDatabase, getMethod.invoke(parameterToUpdated));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
