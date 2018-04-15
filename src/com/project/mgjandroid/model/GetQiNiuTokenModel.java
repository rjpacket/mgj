package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/16.
 */
public class GetQiNiuTokenModel extends Entity {

    /**
     * code : 0
     * uuid : 865543020573963
     * value : {"token":"kpOJI119R-711u6SU84zoHoY2T9SEj1NWnzagim1:Ug39y7qKt0MvsrkU05d62TpdKqI=:eyJzY29wZSI6Im1hZ3VhbmppYSIsImRlYWRsaW5lIjoxNDYzNDYwMDc1fQ==","path":"http://7xpvkm.com1.z0.glb.clouddn.com/"}
     * success : true
     * servertime : 2016-05-17 11:56:42
     */

    private int code;
    private String uuid;
    /**
     * token : kpOJI119R-711u6SU84zoHoY2T9SEj1NWnzagim1:Ug39y7qKt0MvsrkU05d62TpdKqI=:eyJzY29wZSI6Im1hZ3VhbmppYSIsImRlYWRsaW5lIjoxNDYzNDYwMDc1fQ==
     * path : http://7xpvkm.com1.z0.glb.clouddn.com/
     */

    private ValueEntity value;
    private boolean success;
    private String servertime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public static class ValueEntity extends Entity{
        private String token;
        private String path;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
