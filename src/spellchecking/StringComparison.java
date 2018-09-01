package spellchecking;

//Class which contains some utility methods to compare strings
public class StringComparison {

// Compute the edit distance (Levenstein Distance) between strings x
// and y; returns a positive number indicating the minimum character
// insertions, deletions, or substitutions required to transform x
// into y.  Smaller numbers mean x and y are "closer" to each other.
// Uses dynamic programming to solve this task as per the algorithm
// at
// https://en.wikipedia.org/wiki/Levenshtein_distance#Iterative_with_full_matrix.
// 
// Possible to optimize the performance of this using the two-row
// approach or a global matrix though both would introduce
// complications.
public static int editDistance(String x, String y){
 int m=x.length(), n=y.length();

 // Could improve global efficiency if this were statically
 // allocated and only resized when larger words came in. However,
 // this could create problems if tests are run in multiple threads
 // so leaving it as is now.
 int [][] D = new int[m+1][n+1]; // initialized to 0

 // source prefixes can be transformed into empty string by
 // dropping all characters
 for(int i=1; i<=m; i++){
   D[i][0] = i;
 }

 // target prefixes can be reached from empty source prefix
 // by inserting every character
 for(int j=1; j<=n; j++){
   D[0][j] = j;
 }

 // Dynamic programming: fill in the cost matrix from left to
 // right, top to bottom to measure the cost of transforming one
 // string to the other
 for(int j=1; j<=n; j++){
   for(int i=1; i<=m; i++){
     int deleteCost = D[i-1][j] + 1;
     int insertCost = D[i][j-1] + 1;
     int substCost  = D[i-1][j-1] + ((x.charAt(i-1) == y.charAt(j-1)) ? 0 : 1);
     int minCost = deleteCost;
     if(insertCost < minCost){
       minCost = insertCost;
     }
     if(substCost < minCost){
       minCost = substCost;
     }
     D[i][j] = minCost;
   }
 }
 // for(int [] row : D){
 //   System.out.println(Arrays.toString(row));
 // }

 // Final cell of cost matrix contains distance
 return D[m][n];
}
}
