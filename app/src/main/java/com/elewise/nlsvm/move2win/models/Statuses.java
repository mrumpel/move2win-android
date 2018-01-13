package com.elewise.nlsvm.move2win.models;

/**
 * Created by lucenko on 13.01.2018.
 */

public class Statuses {
    public final static int empty = 0; //только создатель комнаты, ожидающий соперника
    public final static int full = 1; //создатель и соперник
    public final static int start = 2; //гонка началась
    public final static int end = 3; //гонка закончилась
    public final static int interrupted = 4; //гонка отменена
    public final static int processed = 5; //ставки обработаны
}
