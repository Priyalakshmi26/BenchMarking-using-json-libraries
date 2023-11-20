/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.example;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import com.google.gson.*;
import java.io.IOException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;


import static org.openjdk.jmh.annotations.Mode.AverageTime;

@BenchmarkMode({Mode.All})
@Fork(value = 1, warmups = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MyBenchmark {
    @Benchmark
    public void jackson(Blackhole blackhole) throws IOException {
        final ObjectMapper objectMapper=new ObjectMapper();
        String json=GetString.getstring();
        blackhole.consume(objectMapper.readValue(json, new TypeReference<List<User>>() {
                }));

    }

    @Benchmark
    public void gson(Blackhole blackhole) throws IOException {

        Gson gson = new Gson();
        String json=GetString.getstring();
        Type listType = new TypeToken<List<User>>() {}.getType();

        blackhole.consume(gson.fromJson(json, listType));
    }

    @Benchmark
    public void fastjson(Blackhole blackhole) throws IOException {

        String json = GetString.getstring();
        List<User> list = JSON.parseArray(json, User.class);
        blackhole.consume(list);
    }
}



class User{
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    public User(int id, String name, String username, String email, String phone, String website) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public User() {
        this.id = 0;
        this.name = null;
        this.username = null;
        this.email = null;
        this.phone = null;
        this.website = null;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
