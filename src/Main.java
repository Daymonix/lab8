
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.gagawa.java.elements.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        try {

            String uri =
                    "https://samples.openweathermap.org/data/2.5/find?q=London&units=imperial&appid=b6907d289e10d714a6e88b30761fae22";

            String json = Utils.readUrl(uri);

            Gson gson = new Gson();
            Page page = gson.fromJson(json, Page.class);
            Html html = new Html();

            Meta m = new Meta("");
            m.setHttpEquiv("Content-Type");
            m.setContent("text/html; charset=utf-8");
            html.appendChild(m);
            Body body = new Body();
            html.appendChild(body);

            Div div = new Div();
            div.setId("main");
            P[] p = new P[8];
            for (int i=0;i<p.length;i++){
                p[i] = new P();
            }
            P[] pWeather = new P[8];
            for (int i=0;i<p.length;i++){
                pWeather[i] = new P();
            }
            Div divWeather = new Div();
            divWeather.setId("wether");
            for (Item item : page.list) {
                p[0].appendText("Название города: " + item.name);
                div.appendChild( p[0] );
                p[1].appendText("Код страны в котором он расположен: " + page.cod);
                div.appendChild(p[1]);
                p[2].appendText("Текущая температура в градусах Цельсия:  " + item.main.temp);
                div.appendChild(p[2]);
                p[3].appendText("Давление в мм ртутного столба: " + item.main.pressure);
                div.appendChild(p[3]);
                p[4].appendText("Влажность в процентах: " + item.main.humidity);
                div.appendChild(p[4]);
                p[5].appendText("Минимальная "+ item.main.temp_min+" максимальная "+item.main.temp_max+" температура за сегодня");
                div.appendChild(p[5]);
                p[6].appendText("Облачность в процентах: "+ item.clouds.all);
                div.appendChild(p[6]);
                p[7].appendText("Скорость и направление ветра :"+item.wind.speed +"m/s"+" deg:"+item.wind.deg);
                div.appendChild(p[7]);

                pWeather[0].appendText("Ид погода: " + item.weather[0].id);
                divWeather.appendChild(pWeather[0]);
                pWeather[1].appendText("ПОгода: " +  item.weather[0].main);
                divWeather.appendChild(pWeather[1]);
                Img image = new Img( "some alt", "http://openweathermap.org/img/w/"+item.weather[0].icon+".png" );
                divWeather.appendChild(image);

                pWeather[3].appendText("Описание: " + item.weather[0].description);
                divWeather.appendChild(pWeather[3]);

                pWeather[4].appendText("Ид погода: " + item.weather[1].id);
                divWeather.appendChild(pWeather[4]);
                pWeather[5].appendText("ПОгода: " +  item.weather[1].main);
                divWeather.appendChild(pWeather[5]);
                pWeather[6].appendText("Иконка :"+ item.weather[1].icon);
                divWeather.appendChild(pWeather[6]);
                image = new Img( "some alt", "http://openweathermap.org/img/w/"+item.weather[1].icon+".png" );
                divWeather.appendChild(image);
                pWeather[7].appendText("Описание: " + item.weather[1].description);
                divWeather.appendChild(pWeather[7]);
                div.appendChild(divWeather);
            }
            body.appendChild(div);
            html.appendChild(body);
            Utils.saveHTML( html.write() );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
