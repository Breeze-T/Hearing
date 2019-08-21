package com.runtime.service;

import com.runtime.pojo.systemStatusPojo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface systemStatusService extends Remote{
    public systemStatusPojo getRuntime() throws RemoteException;//内存 M 网络 kb/s
}
