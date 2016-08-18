package com.ankoma88;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by ankoma88 on 18.08.16.
 */
public class SkiTaskMain {

    private Map<String, String> mapNodeToRoute = new HashMap<>();

    private List<String> tempRoutes = new ArrayList<>();

    private String getLongestPath(Integer[][] routesMatrix) {
        String maxRoute = "";

        for (int i = 0; i < routesMatrix.length; i++) {
            for (int j = 0; j < routesMatrix[i].length; j++) {
                String result = getLongestPath(routesMatrix, i, j);

                if (getSize(result) > getSize(maxRoute))
                    maxRoute = result;
            }
        }

        mapNodeToRoute.clear();
        return maxRoute;
    }

    private String getLongestString(String[] array) {
        int maxLength = 0;
        String longestString = null;
        for (String s : array) {
            if (getSize(s) >= maxLength) {
                if (getSize(s) > maxLength) {
                    maxLength = getSize(s);
                    longestString = s;
                }
            }
        }
        if (longestString == null) {
            longestString = "";
        }
        return longestString;
    }

    private String getLongestPath(Integer[][] matrix, int row, int col) {
        String maxRoute;

        String leftDirection = getLeftDirection(matrix, row, col);
        String rightDirection = getRightDirection(matrix, row, col);
        String upDirection = getUpDirection(matrix, row, col);
        String downDirection = getDownDirection(matrix, row, col);

        String[] routes = {leftDirection, rightDirection, upDirection, downDirection};

        maxRoute = getLongestString(routes);

        maxRoute += " " + matrix[row][col];
        String key = row + " " + col;
        mapNodeToRoute.put(key, maxRoute);

        tempRoutes.add(maxRoute);
        return maxRoute;
    }

    private int getSize(String leftDirection) {
        return leftDirection.split(" ").length;
    }

    private String getLeftDirection(Integer[][] matrix, int row, int col) {
        String leftDirection = "";
        int left = col - 1;

        if (left >= 0 && matrix[row][left] < matrix[row][col]) {
            String leftKey = row + " " + left;

            if (mapNodeToRoute.containsKey(leftKey)) {
                leftDirection = mapNodeToRoute.get(leftKey);
            } else
                leftDirection = getLongestPath(matrix, row, left);
        }
        return leftDirection;
    }

    private String getRightDirection(Integer[][] matrix, int row, int col) {
        String rightDirection = "";
        int right = col + 1;

        if (right < matrix[0].length && matrix[row][right] < matrix[row][col]) {
            String rightKey = row + " " + right;

            if (mapNodeToRoute.containsKey(rightKey))
                rightDirection = mapNodeToRoute.get(rightKey);
            else
                rightDirection = getLongestPath(matrix, row, right);
        }

        return rightDirection;
    }

    private String getUpDirection(Integer[][] matrix, int row, int col) {
        String upDirection = "";
        int up = row - 1;

        if (up >= 0 && matrix[up][col] < matrix[row][col]) {
            String upKey = up + " " + col;

            if (mapNodeToRoute.containsKey(upKey))
                upDirection = mapNodeToRoute.get(upKey);
            else
                upDirection = getLongestPath(matrix, up, col);
        }

        return upDirection;
    }

    private String getDownDirection(Integer[][] matrix, int row, int col) {
        String downDirection = "";
        int down = row + 1;

        if (down < matrix.length && matrix[down][col] < matrix[row][col]) {
            String downKey = down + " " + col;

            if (mapNodeToRoute.containsKey(downKey))
                downDirection = mapNodeToRoute.get(downKey);
            else
                downDirection = getLongestPath(matrix, down, col);
        }

        return downDirection;
    }

    public static void main(String arg[]) {
        System.out.println("Result:");
        BufferedReader br = null;
        String filePath = Paths.get(".", "src", "data.txt").normalize().toFile().getPath();
        SkiTaskMain skiTaskMain = new SkiTaskMain();
        try {
            Integer[][] matrix = null;
            String sCurrentLine;
            int count = 0;
            br = new BufferedReader(new FileReader(filePath));
            sCurrentLine = br.readLine();

            while (sCurrentLine != null) {
                String[] tokens = sCurrentLine.split(" ");

                if (tokens.length == 2) {
                    if (matrix != null) {
                        String result = skiTaskMain.getLongestPath(matrix);
                        skiTaskMain.selectSteepestPath(result);
                    }
                    count = 0;
                    Integer row = Integer.valueOf(tokens[0]);
                    Integer col = Integer.valueOf(tokens[1]);
                    matrix = new Integer[row][col];

                } else {
                    for (int i = 0; i < tokens.length; i++) {
                        if (matrix != null) {
                            matrix[count][i] = Integer.valueOf(tokens[i]);
                        }
                    }
                    count++;
                }
                sCurrentLine = br.readLine();
            }
            if (matrix != null) {
                String result = skiTaskMain.getLongestPath(matrix);
                skiTaskMain.selectSteepestPath(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void selectSteepestPath(String result) {
        List<String> possibleResults = new ArrayList<>();
        int resultLength = getSize(result);
        for (String s : tempRoutes) {
            if (getSize(s) == resultLength) {
                if (s != null && !s.isEmpty()) {
                    possibleResults.add(s);
                }
            }
        }

        String[] resultPath = possibleResults.get(0).split(" ");
        int lastPosition = 0;
        for (String s : possibleResults) {
            String[] res = s.split(" ");
            int lastPos = Integer.parseInt(res[res.length - 1]);

            if (lastPos > lastPosition) {
                lastPosition = lastPos;
                resultPath = res;
            }

        }

        reverseAndPrint(resultPath);

    }

    private void reverseAndPrint(String array[]){
        List<String> list = Arrays.asList(array);
        Collections.reverse(list);

        for (String s : list) {
            System.out.print(s + " ");
        }
    }

}

