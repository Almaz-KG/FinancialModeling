package org.projectgermes.incubator;

import java.util.Scanner;

/**
 * Created by Almaz on 31.07.2014.
 */
public class ProjectGermes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Project Germes\nVersion 0.0.1 beta");
        System.out.println("Здравствуйте, представьтесь пожалуйста");
        System.out.print("-> ");
        String name = sc.nextLine();
        System.out.println("Введите название компании");
        System.out.print("-> ");
        String company = sc.nextLine();

        System.out.println("Решением крупного промышленного концерна " + name +" назначен " +
                "президентом компании " + company);
        System.out.println("На данный момент компания владеет 2 фабриками:");
        System.out.println("1. Текстильная фабрика");
        System.out.println("2. Обувная фабрика");
        System.out.println("Каждый месяц компания покупает сырье, обрабатывает его" +
                " и продает изготовленную продукцию своим клиентам.");
        System.out.println(name + " теперь Вам придется решать, сколько и каких" +
                " товаров выпускать, стоит ли и когда именно расширять производственные" +
                " мощности, как финансировать их расширение. ");

        System.out.println();
    }
}
