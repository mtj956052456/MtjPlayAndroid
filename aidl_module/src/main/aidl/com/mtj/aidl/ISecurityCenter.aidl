package com.mtj.aidl;

/**
 * @author mtj
 * @time 2019/12/8 2019 12
 * @des
 */
interface ISecurityCenter {

    String encrypt(String content);

    String decrypt(String password);
}
