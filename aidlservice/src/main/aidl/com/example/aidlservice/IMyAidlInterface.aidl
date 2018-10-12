package com.example.aidlservice;

import com.example.aidlservice.Book;

interface IMyAidlInterface {
    void transmitSimpleData(int num);
    void transmitComplicatedData(in Book book);
}
