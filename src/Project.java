import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;


public class Project {

    //for questions and answers
    static ArrayStack QA = new ArrayStack();

    //y coord position of text in review screen
    //each q&a pair has its own yPosition
    static int[] yPositions;

    public static void main(String[] args) {

        //If there is command line arg print err and exit
        if (args.length != 0) {
            System.err.print("No command line arguments needed");
            System.exit(1);
        }

        //so that screen doesn't flicker
        StdDraw.enableDoubleBuffering();

        //Set canvas
        StdDraw.setCanvasSize(960, 720);
        StdDraw.setPenRadius(1);
        StdDraw.setXscale(0, 960);
        StdDraw.setYscale(0, 720);

        //set to start screen
        int screen = 1;

        //category of question
        int category = 0;

        while (true) {

            StdDraw.clear();

            //Draw the screen
            if (screen == 1) {
                StdDraw.picture(480, 360, "images/StartScreen.jpg");
            } else if (screen == 2) {
                StdDraw.picture(480, 360, "images/CheckAll.jpg");
            } else if (screen == 3) {
                StdDraw.picture(480, 360, "images/CheckB.jpg");
            } else if (screen == 4) {
                StdDraw.picture(480, 360, "images/CheckQ.jpg");
            } //screen with questions
            else if (screen == 5) {
                StdDraw.picture(480, 360, "images/SecondScreen.jpg");

                //show the randomly generated question
                String qu = question(category);
                StdDraw.text(477, 518, qu);

                //add the question onto the review answers stack
                QA.push(qu);

                //update y position of q&a (for review screen)
                yPositions = new int[QA.size()];
                int startingY = 550;
                for (int i = 0; i < yPositions.length; i++) {
                    yPositions[i] = startingY - i * 60;
                }

                //text that is typed by user and displayed on screen
                String displayed = "";

                //needs to pause so that the text doesn't immediately go away
                boolean clickedNext = false;

                while (!clickedNext) {

                    //allow users to enter letters
                    if (StdDraw.hasNextKeyTyped()) {

                        //if backspace, delete the last char
                        char typed = StdDraw.nextKeyTyped();
                        if (typed == '\b') {
                            if (displayed.isEmpty()) displayed = "";
                            else displayed = displayed.substring(0, displayed.length() - 1);
                        } else {
                            displayed += typed;
                        }

                    }

                    //if user clicks on screen
                    if (StdDraw.isMousePressed()) {
                        double x2 = StdDraw.mouseX();
                        double y2 = StdDraw.mouseY();
//                        System.out.println(x2 + " " + y2);

                        //go to next question if pressed next
                        if (x2 <= 588 && x2 >= 368 && y2 <= 92 && y2 >= 34) {


                            if(displayed.isEmpty()) {
                                QA.pop();
                            }
                            else QA.push(displayed);

                            clickedNext = true;
                        }

                        //go back to main menu if pressed home
                        if (x2 <= 226 && x2 >= 49 && y2 <= 688 && y2 >= 633) {
                            screen = 1;
                            clickedNext = true;
                        }

                        //if pressed review answers
                        if (x2 <= 897 && x2 >= 721 && y2 <= 688 && y2 >= 633) {
                            screen = 6;

                            if(displayed.isEmpty()) QA.pop();
                            else QA.push(displayed);
                            clickedNext = true;
                        }


                    }

                    //keep the questions and typed on screen
                    StdDraw.clear();
                    StdDraw.picture(480, 360, "images/SecondScreen.jpg");
                    StdDraw.text(477, 518, qu);
                    StdDraw.textLeft(10, 300, displayed);
                    StdDraw.show();

                }


            } else if (screen == 6) {
                StdDraw.picture(480, 360, "images/Review.jpg");

                if (StdDraw.isMousePressed()) {
                    double x2 = StdDraw.mouseX();
                    double y2 = StdDraw.mouseY();
//                    System.out.println(x2 + " " + y2);

                    //go back to main menu if pressed home
                    if (x2 <= 226 && x2 >= 49 && y2 <= 688 && y2 >= 633) {
                        screen = 1;
                    }

                    //clear all answers and questions if pressed clear
                    if (x2 <= 897 && x2 >= 721 && y2 <= 688 && y2 >= 633) {
                        QA = new ArrayStack();
                        yPositions = new int[0];

                        StdDraw.clear();
                        StdDraw.picture(480, 360, "images/Review.jpg");
                        StdDraw.show();
                    }

                    //if pressed up arrow, scroll up
                    if (x2 <= 924 && x2 >= 806 && y2 <= 486 && y2 >= 371) {
                        for (int i = 0; i < yPositions.length; i++) {
                            yPositions[i] += 60;
                        }
                    }

                    //if pressed down arrow, scroll down
                    if (x2 <= 924 && x2 >= 806 && y2 <= 274 && y2 >= 160) {
                        for (int i = 0; i < yPositions.length; i++) {
                            yPositions[i] -= 60;
                        }
                    }
                }

                StdDraw.clear();
                StdDraw.picture(480, 360, "images/Review.jpg");

                int qaIndex = 0;

                //get every q&a until array is empty
                for (int i = QA.size() - 1; i >= 1; i -= 2) {
                    String answer = QA.arr[i];
                    String qs = QA.arr[i-1];

                    if (qs != null && answer != null) {
                        int currentY = yPositions[qaIndex];

                        //only display if it doesn't go off-screen
                        if (currentY >= 34 && currentY <= 575) {
                            StdDraw.textLeft(60, currentY, qs);
                            StdDraw.textLeft(60, currentY - 20, answer);
                        }

                        // move to the next q&a pair
                        qaIndex++;
                    }

                }

                StdDraw.show();


            }

            //if user pressed somewhere on the screen
            if (StdDraw.isMousePressed()) {
                //get x & y coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
//                System.out.println("(" + x + ", " + y + ")");

                //show check mark when pressed
                if (screen == 1 || screen == 2 || screen == 3 || screen == 4) {
                    if (163 <= x && x <= 797 && y <= 477 && y >= 391) {
                        screen = 2;
                        category = 1;
                    } else if (163 <= x && x <= 797 && y <= 345 && y >= 260) {
                        screen = 3;
                        category = 2;
                    } else if (163 <= x && x <= 797 && y <= 215 && y >= 134) {
                        screen = 4;
                        category = 3;
                    }
                }

                //If pressed next, go to second screen
                if (screen == 2 || screen == 3 || screen == 4) {
                    if (x <= 588 && x >= 368 && y <= 92 && y >= 34) {
                        screen = 5;
                    }

                }

            }

            StdDraw.show();
            StdDraw.pause(10);
        }

    }

    //randomly generate question
    public static String question(int category) {

        String name = null;

        //which category
        if (category == 1) {
            name = "textfile/All.txt";
        }
        if (category == 2) {
            name = "textfile/Behavioral.txt";
        }
        if (category == 3) {
            name = "textfile/Technical.txt";
        }

        ArrayStack arrStack = new ArrayStack();

        try {
            File myObj = new File(name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String q = myReader.nextLine();
                arrStack.push(q);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        //randomly pick a question from the array stack
        int arrSize = arrStack.size();
        Random rnd = new Random();
        int qNum = rnd.nextInt(arrSize);

        return arrStack.arr[qNum];

    }
}
