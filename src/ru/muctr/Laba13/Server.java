package ru.muctr.Laba13;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * TODO: измените код сервера так, чтобы он осуществлял пересчет валют (например, из рублей в евро).
 * Пересчет валют организуйте в отдельном методе (private static String convertCurrency(String sum))
 * Примечание: код клиента править не нужно.
 */

public class Server {
    private static final int PORT = 8189;
    private static ServerSocket server;
    private static Socket socket;

    private static Double convertCurrency(double summ) {
        summ=summ/74;
        return summ;
    }

    public static void main(String[] args) throws IOException {
        try {
            server = new ServerSocket(PORT); //в конструктор серверного сокета передаем порт, который он будет слушать
            System.out.println("Server started");
            socket = server.accept();  //ожидание подключения клиента
            System.out.println("Client connected");

            Scanner scanner = new Scanner(socket.getInputStream());  //входящий поток для получения сообщений от клиента
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //исходящий поток для отправки сообщений клиенту

            while (true) {             // бесконечно ожидаем сообщения от клиента
                String str = scanner.nextLine();  //в перемнную str записываем сообщение от клиента
                if (str.equals("/end")) {
                    System.out.println("Client disconnected");
                    break;
                }
                System.out.println("Client: " + str);
                try {
                    double d = Double.valueOf(str); //Конвертируем строку в число
                    d = convertCurrency(d);
                    out.println("ECHO: " + str);//эхо-сервер возвращает клиенту его сообщение
                    out.println("$: " + d);
                } catch (Exception f) {
                    f.printStackTrace();}

                }
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
