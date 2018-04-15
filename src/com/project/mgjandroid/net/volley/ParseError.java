/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.mgjandroid.net.volley;

import com.project.mgjandroid.net.volley.Cache;
import com.project.mgjandroid.net.volley.NetworkResponse;
import com.project.mgjandroid.net.volley.VolleyError;

/**
 * Indicates that the server's response could not be parsed.
 */
@SuppressWarnings("serial")
public class ParseError extends VolleyError {

    private String response;
    private Cache.Entry cacheEntry;

    public ParseError() { }

    public ParseError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ParseError(Throwable cause, String response, Cache.Entry cacheEntry) {
        super(cause);
        this.response = response;
        this.cacheEntry = cacheEntry;
    }

    public ParseError(Throwable cause) {
        super(cause);
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Cache.Entry getCacheEntry() {
        return cacheEntry;
    }

    public void setCacheEntry(Cache.Entry cacheEntry) {
        this.cacheEntry = cacheEntry;
    }
}
