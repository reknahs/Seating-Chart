// Note: 
//       The desk layout is a 14x14 grid, where the user can choose where to place his desks and students
//       If seat value is 0, then there is no desk at that coordinate.
//       If the seat value is 1, then there is a desk at that coordinate, but no student there.
//       If the seat value is an integer which is not 1, then there is a desk and a student at that coordinate.
//       





public class Room {
    private int[][] room;
                        
    public Room() {
       //displayGrid();
       for (int x = 0; x < 14; x++ ) { // x is the row number
           for (int y = 0; y < 14; y++) { // y is the collumn number
               room[x][y] = 0;
        }
        }
    }

    public void addSeat(int x, int y) {
        // Adds seat at row = x-1 , and collumn = y-1
        // Changes seat value from 0 to 1
        room[x][y] = 1;

        
    }
// getLocation(Student student), returns an int[] of size 2 with indices of position of student in 
    public int[] getLocation(Student student) {
        for (int x = 0; x < 14; x++ ) { 
            for (int y = 0; y < 14; y++) {
                if (room[x][y] == student.getId()) {
                    int[] coordinate = {x, y};
                    return coordinate;
                }
            }
        }
        int[] coord = new int[2];
        return coord;
            
}
    public void addStudent(int x, int y, int id) {
        room[x][y] = id; 
        
    }

    public boolean isSeatEmpty(int x, int y) {
        // if seat value = 0:
        if (room[x][y] == 0) {
            return true;
        }

        if (room[x][y] == 1) {
            return false;
        }

        else {
        return true;
        }

    }
    public int studentWhoSitsHere(int x, int y) {
        if (room[x][y] != 0 || room[x][y] != 1) {
            int student_id = room[x][y];
            return student_id; }
        else {
        return -1;
        }
            

        }
    
            
        

    }
