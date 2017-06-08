/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.stableloan.model;

public class UpdateInfoBean {
    // 是否有新版本
    public boolean hasUpdate = false;
    // 是否静默下载：有新版本时不提示直接下载
    public boolean isSilent = false;
    // 是否强制安装：不安装无法使用app
    public boolean isForce = false;
    // 是否下载完成后自动安装
    public boolean isAutoInstall = true;
    // 是否可忽略该版本
    public boolean isIgnorable = true;
    // 一天内最大提示次数，<1时不限
    public int maxTimes = 0;
    public int versionCode;
    public String versionName;
    public String updateContent;

    public String url;
    public String md5;
    public long size;

    public UpdateInfoBean() {
    }

    public boolean isHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public boolean isAutoInstall() {
        return isAutoInstall;
    }

    public void setAutoInstall(boolean autoInstall) {
        isAutoInstall = autoInstall;
    }

    public boolean isIgnorable() {
        return isIgnorable;
    }

    public void setIgnorable(boolean ignorable) {
        isIgnorable = ignorable;
    }

    public int getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UpdateInfoBean{" +
                "hasUpdate=" + hasUpdate +
                ", isSilent=" + isSilent +
                ", isForce=" + isForce +
                ", isAutoInstall=" + isAutoInstall +
                ", isIgnorable=" + isIgnorable +
                ", maxTimes=" + maxTimes +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", url='" + url + '\'' +
                ", md5='" + md5 + '\'' +
                ", size=" + size +
                '}';
    }
}