package demawi.ayto.service;

import java.util.function.Consumer;

import demawi.ayto.modell.AYTO_Data;

public interface IMatchFinder {

   void setOut(Consumer<String> out);

   void printDayResults(AYTO_Data data, int tagNr);

}
