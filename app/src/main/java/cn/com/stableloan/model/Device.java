package cn.com.stableloan.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/10/13.
 */

public class Device implements Serializable {

    /**
     * code : 200
     * message : 0
     * data : {"list":[{"id":1,"user_id":242,"device":"swewe","version_number":"sdgs"}],"is_device":1}
     * error_code : 0
     * error_message :
     * time : 2017-10-16 10:21:32
     */

    private int code;
    private int message;
    private DataBean data;
    private int error_code;
    private String error_message;
    private String time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class DataBean implements Serializable{
        /**
         * list : [{"id":1,"user_id":242,"device":"swewe","version_number":"sdgs"}]
         * is_device : 1
         */

        private int is_device;
        private List<ListBean> list;

        public int getIs_device() {
            return is_device;
        }

        public void setIs_device(int is_device) {
            this.is_device = is_device;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 1
             * user_id : 242
             * device : swewe
             * version_number : sdgs
             */

            private int id;
            private int user_id;
            private String device;
            private String version_number;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
            }

            public String getVersion_number() {
                return version_number;
            }

            public void setVersion_number(String version_number) {
                this.version_number = version_number;
            }
        }
    }
}
