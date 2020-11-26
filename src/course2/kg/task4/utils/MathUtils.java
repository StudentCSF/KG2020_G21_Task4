package course2.kg.task4.utils;



public class MathUtils {

    public static float determinant(float[][] matrix) {
        return countDet(matrix);
    }

    private static float countDet(float[][] arr) {
        if (arr.length == 1) {
            return arr[0][0];
        }
        if (arr.length == 2) {
            return arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
        }

        float det = 0;

        for (int k = 0; k < arr.length; k++) {

            if (arr[0][k] == 0) continue;

            float[][] arr2 = new float[arr.length - 1][arr.length - 1];

            for (int i = 0; i < arr2.length; i++) {
                int add = 0;
                for (int j = 0; j < arr2.length; j++) {
                    if (j == k) {
                        add++;
                    }
                    arr2[i][j] = arr[i + 1][j + add];
                }
            }
            float add = arr[0][k] * countDet(arr2);
            add *= (k + 2) % 2 == 0 ? 1 : -1;
            det += add;
        }
        return det;
    }
}
