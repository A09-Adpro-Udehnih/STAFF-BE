package com.example.staffbe.strategy;

import java.util.List;

public interface GetAllStrategy<T> {
    List<T> findAll();  // Mendapatkan semua entitas dari database
}