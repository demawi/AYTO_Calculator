package demawi.ayto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;

import demawi.ayto.modell.SeasonData;

public class InstanceFinder {

   public <T> LinkedHashSet<T> findAllInstances(Class<?> subClassesOf) {
      LinkedHashSet<T> result = new LinkedHashSet<>();
      findAllInstances(InstanceFinder.class.getPackage(), subClassesOf, result);
      return result;
   }

   public <T> void findAllInstances(Package curPackage, Class<?> subClassesOf, LinkedHashSet<T> result) {
      findAllInstances(curPackage.getName(), subClassesOf, result);
   }

   public <T> void findAllInstances(String packageName, Class<?> subClassesOf, LinkedHashSet<T> result) {
      InputStream stream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.replaceAll("[.]", "/"));

      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      reader.lines()
            .forEach(line -> {
               if (line.endsWith(".class")) {
                  Class<?> foundClass = getClass(line, packageName);
                  if (subClassesOf.isAssignableFrom(foundClass) && !subClassesOf.equals(foundClass)) {
                     try {
                        result.add((T) foundClass.getDeclaredConstructor()
                              .newInstance());
                     }
                     catch (Exception e) {
                        throw new RuntimeException(e);
                     }
                  }
               }
               else if (!line.contains(".")) {
                  findAllInstances(packageName + "." + line, subClassesOf, result);
               }
            });
   }

   private Class getClass(String className, String packageName) {
      try {
         return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
      }
      catch (ClassNotFoundException e) {
         // handle the exception
      }
      return null;
   }

   public static void main(String[] args)
         throws Exception {
      new InstanceFinder().findAllInstances(SeasonData.class)
            .forEach(c -> {
               System.out.println(c);
            });
   }

}
