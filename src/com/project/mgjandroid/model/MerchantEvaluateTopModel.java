package com.project.mgjandroid.model;

import com.project.mgjandroid.bean.Merchant;

/**
 * Created by ning on 2016/4/5.
 */
public class MerchantEvaluateTopModel extends Entity {
    private int code;
    private String uuid;
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
        private Merchant merchant;
        private ShareInfoEntity shareInfo;
        private boolean favorite;

        public Merchant getMerchant() {
            return merchant;
        }

        public void setMerchant(Merchant merchant) {
            this.merchant = merchant;
        }

        public ShareInfoEntity getShareInfo() {
            return shareInfo;
        }

        public void setShareInfo(ShareInfoEntity shareInfo) {
            this.shareInfo = shareInfo;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }

        public static class ShareInfoEntity extends Entity{
//            "memo": "ebeecake",
//            "img": "http://7xpvkm.com1.z0.glb.clouddn.com/201604061517484591678",
//            "url": "http://120.24.16.64/maguanjia/merchantShare.html?merchantId=8"

            private String memo;
            private String img;
            private String url;

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
