package com.example.caculator;

import java.util.Stack;

public class eval {
    public static String inorder(String str) { //中缀转后缀
        Stack<Character> stack = new Stack<>();
        char x[] = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < x.length; i++) {
            if (Character.isDigit(x[i])||x[i]=='.') {
                stringBuilder.append(x[i]);
                if (((i < x.length - 1 && !(Character.isDigit(x[i + 1])||x[i+1]=='.') || i == x.length - 1))){
                    stringBuilder.append(" ");
                }
                continue;
            }
            if (x[i] == '*' || x[i] == '(' || x[i] == '/') {
                stack.push(x[i]);
                continue;
            }
            if (x[i] == ')') {
                while (!stack.isEmpty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                        break;
                    }
                    stringBuilder.append(stack.pop());
                }
                continue;
            }
            if (x[i] == '+' || x[i] == '-') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    stringBuilder.append(stack.pop());
                }
                stack.push(x[i]);
                continue;
            }
        }
        while (!stack.isEmpty())
            stringBuilder.append(stack.pop());
        return stringBuilder.toString();
    }

    public Float caculate(String str) {//计算
        Stack<Float> num = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        char x[] = str.toCharArray();
        for (int i = 0; i < x.length; i++) {
            if (Character.isDigit(x[i])||x[i]=='.') {
                stringBuilder.append(x[i]);
                if (i + 1 == x.length) {
                    num.push(Float.parseFloat(stringBuilder.toString()));
                }
                if (x[i + 1] == ' ') {
                    num.push(Float.parseFloat(stringBuilder.toString()));
                    stringBuilder.delete(0, stringBuilder.length());
                    continue;
                }
            }
            if (x[i] == '+') {
                num.push(num.pop() + num.pop());
            }
            if (x[i] == '-') {
                float a = num.pop();
                float b = num.pop();
                num.push(b - a);
            }
            if (x[i] == '*') {
                num.push(num.pop() * num.pop());
            }
            if (x[i] == '/') {
                float a = num.pop();
                float b = num.pop();
                num.push(b / a);
            }
        }
        return num.pop();
    }
}
