 public class MagicSnailSolver {
     public static boolean isValueSafe(int[][] square,
                                       int row, int col,
                                       int value) {
         int rowCountZero = 0;  // Az előforduló nullák a sorban
         int colCountZero = 0;  // Az előforduló nullák az oszlopban

         for (int i = 0; i < square.length; i++) {
             //Ellenőrzi hogy a sornak van-e már értéke
             if (square[row][i] == value) {
                 if (value == 0) {
                     rowCountZero++;
                     if (rowCountZero > 2)
                         return false; //Hamis, ha a már 3db nulla van a sorban
                 } else {
                     return false;
                 }
             }
         }

         for (int i = 0; i < square.length; i++) {
             // Ellenőrzi hogy az oszlopnak van-e már értéke
             if (square[i][col] == value) {
                 if (value == 0) {
                     colCountZero++;
                     if (colCountZero > 2)
                         return false; // Hamis, ha a már 3db nulla van az oszlopban
                 } else {
                     return false;
                 }
             }
         }

         int prevNum = square[row][col];
         square[row][col] = value; // A feltételezett értékeket átadjuk spirál ellenőrzésre

         boolean checkSpirally = true;

         // Ellenörzi hogy minden oszlop és sornak van 1,2,3 értéke
         for (int i = 0; i < square.length; i++) {
             boolean foundOneRow = false;
             boolean foundTwoRow = false;
             boolean foundThreeRow = false;

             boolean foundOneCol = false;
             boolean foundTwoCol = false;
             boolean foundThreeCol = false;
             for (int j = 0; j < square.length; j++) {
                 if (square[i][j] == 1) foundOneRow = true;
                 if (square[i][j] == 2) foundTwoRow = true;
                 if (square[i][j] == 3) foundThreeRow = true;

                 if (square[j][i] == 1) foundOneCol = true;
                 if (square[j][i] == 2) foundTwoCol = true;
                 if (square[j][i] == 3) foundThreeCol = true;
             }
             if (!(foundOneRow && foundTwoRow && foundThreeRow)) checkSpirally = false;
             if (!(foundOneCol && foundTwoCol && foundThreeCol)) checkSpirally = false;
         }
         if (checkSpirally && !spiralCheck(square)) {
             // Ha a spirál ellenőrzés elbukik akkor állítsa vissza az értékeket
             square[row][col] = prevNum;
             return false;
         }
         // Ha nincs ütközés akkor jó
         return true;
     }

     static boolean spiralCheck(int square[][]) {
         int rowIndexStart = 0;
         int colIndexStart = 0;
         int rowIndexEnd = square.length;
         int colIndexEnd = square.length;

         int[] cellValuesSpiralOrder = new int[18];
         int index = 0;

         // Ellenőrizzük hogy a számok az 1-2-3 sorrendben vannak
         while (rowIndexStart < rowIndexEnd && colIndexStart < colIndexEnd) {
             // Ellenörzi az első megmaradt  sort
             for (int i = colIndexStart; i < colIndexEnd; ++i) {
                 int num = square[rowIndexStart][i];
                 if (num != 0 && num != -1) {
                     cellValuesSpiralOrder[index] = num;
                     index++;
                 }
             }
             rowIndexStart++;

             // Ellenörzi az utolsó megmaradt  oszlopot
             for (int i = rowIndexStart; i < rowIndexEnd; ++i) {
                 int num = square[i][colIndexEnd - 1];
                 if (num != 0 && num != -1) {
                     cellValuesSpiralOrder[index] = num;
                     index++;
                 }
             }
             colIndexEnd--;

             // Ellenörzi az utolsó megmaradt  sort
             if (rowIndexStart < rowIndexEnd) {
                 for (int i = colIndexEnd - 1; i >= colIndexStart; --i) {
                     int num = square[rowIndexEnd - 1][i];
                     if (num != 0 && num != -1) {
                         cellValuesSpiralOrder[index] = num;
                         index++;
                     }
                 }
                 rowIndexEnd--;
             }

             // Ellenörzi az első megmaradt oszlopot
             if (colIndexStart < colIndexEnd) {
                 for (int i = rowIndexEnd - 1; i >= rowIndexStart; --i) {
                     int num = square[i][colIndexStart];
                     if (num != 0 && num != -1) {
                         cellValuesSpiralOrder[index] = num;
                         index++;
                     }
                 }
                 colIndexStart++;
             }
         }
         for (int i = 1; i < cellValuesSpiralOrder.length; i++) {
             int prevValue = cellValuesSpiralOrder[i - 1];
             int value = cellValuesSpiralOrder[i];
             if (i == 1 && prevValue != 1) return false;
             if (prevValue != 3 && value != prevValue + 1) return false;
             if (prevValue == 3 && value != 1) return false;
         }
         return true;
     }


     public static boolean solveMagicSnail(int[][] square, int l) {
         int row = -1;
         int col = -1;
         boolean allCellsOccupied = true;
         for (int i = 0; i < l; i++) {
             for (int j = 0; j < l; j++) {
                 if (square[i][j] == -1) {
                     row = i;
                     col = j;
                     allCellsOccupied = false;
                     break;
                 }
             }
             if (!allCellsOccupied) {
                 break;
             }
         }

         // nem maradt üres cella
         if (allCellsOccupied) {
             return true;
         }

         // Ha vannak ki nem töltött cellák, akkor kezdje az első üres cekkával és keressen vissza minden sort
         for (int num = 0; num <= 3; num++) {
             if (isValueSafe(square, row, col, num)) {
                 square[row][col] = num;
                 if (solveMagicSnail(square, l)) { // Rekurzív módon ellenőrizzük a megoldást a következő ki nem töltött cellák helyzete szempontjából


                     return true;
                 } else {
                     square[row][col] = -1; // Ha az érték nem felel meg a négyzetkorlátozásoknak, akkor cserélje ki -1-re, hogy ezt megismételhesse a következő hívás során.
                 }
             }
         }
         return false;
     }

     public static void printMagicSnail(int[][] square) {
         for (int i = 0; i < square.length; i++) {
             for (int j = 0; j < square.length; j++) {
                 System.out.print(square[i][j]);
                 System.out.print(" ");
             }
             System.out.print("\n");
         }
     }

     public static void main(String args[]) {

         //int[][] square = new int[6][6];
        /*for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square.length; j++) {
                square[i][j] = -1;
            }
        }*/
         int square[][] = new int[][]{
                 {-1, -1, -1, -1, -1, -1},
                 {-1, -1, -1, -1, -1, -1},
                 {2, -1, 3, -1, -1, -1},
                 {1, -1, -1, -1, -1, -1},
                 {-1, -1, -1, 2, -1, -1},
                 {-1, -1, 1, 3, -1, -1}
         };

         if (solveMagicSnail(square, square.length)) {
             printMagicSnail(square);
         } else {
             System.out.println("No solution");
         }
     }
 }
