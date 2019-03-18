package com.dxnet.ldap.commons.utils;

import com.dxnet.ldap.database.entities.Usex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextOperations;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LdapUtils {

    private static final Logger Log = LoggerFactory.getLogger(LdapUtils.class);


    public static Usex mapAttributesFromLdapToUsex(Attributes attributes) {
        Usex usex = new Usex();
        Field[] ldapFields = usex.getClass().getFields();
        for (Field usexField : ldapFields) {
            String fieldName = usexField.getName();
            if (attributes.get(fieldName) != null) {
                try {
                    String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = usex.getClass().getMethod(methodName, usexField.getType());
                    if (usexField.getType().equals(String.class)) {
                        method.invoke(usex, attributes.get(fieldName).get().toString());
                    } else if (usexField.getType().equals(String[].class)) {
                        List<String> stringList = new ArrayList<>();
                        NamingEnumeration<?> iterate = attributes.get(fieldName).getAll();
                        int count = 0;
                        while (iterate.hasMore()) {
                            stringList.add(iterate.next().toString());
                            count++;
                        }
                        method.invoke(usex, new Object[]{stringList.toArray(new String[0])});
                    }
                } catch (NoSuchMethodException e) {
                    Log.error("method does not exist: " + "set" + fieldName);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return usex;
    }


    public static void mapToContext(Usex person, DirContextOperations context) {
        Field[] usexFields = person.getClass().getFields();
        for (Field usexField : usexFields) {
            try {
                String methodName = "get" + usexField.getName().substring(0, 1).toUpperCase() + usexField.getName().substring(1);
                Method method = person.getClass().getMethod(methodName);
                if (method.invoke(person) != null) {
                    if (usexField.getType().equals(String.class)) {
                        context.setAttributeValues(usexField.getName(), new String[]{(String) method.invoke(person)});
                    } else if (usexField.getType().equals(String[].class)) {
                        context.setAttributeValues(usexField.getName(), (String[])method.invoke(person));
                    }
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
