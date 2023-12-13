package demawi.ayto.permutation;

import java.util.Collection;

public interface Markable {

   boolean hasMark(Mark mark);

   static boolean hasMark(Collection<?> objects, Mark mark) {
      for(Object obj : objects) {
         if(obj instanceof Markable) {
            if(((Markable)obj).hasMark(mark)) {
               return true;
            }
         }
      }
      return false;
   }

}
