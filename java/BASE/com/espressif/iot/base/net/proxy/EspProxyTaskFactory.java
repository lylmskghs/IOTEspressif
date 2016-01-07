package com.espressif.iot.base.net.proxy;

import java.io.IOException;

import org.apache.http.protocol.HTTP;

import android.text.TextUtils;

public class EspProxyTaskFactory
{
    private static final boolean DEBUG = true;
    private static final boolean USE_LOG4J = true;
    private static final Class<?> CLASS = EspProxyTaskFactory.class;
    
    /**
     * create EspProxyTask by its source socket
     * 
     * @param srcSock the source socket
     * @return the EspProxyTask
     */
    public static EspProxyTask createProxyTask(EspSocket srcSock)
    {
        try
        {
            byte[] buffer = new byte[2048];
            int headerLength = EspSocketUtil.readHttpHeader(srcSock.getInputStream(), buffer, 0);
            
            String bssid =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_MESH_BSSID);
            String host =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_MESH_HOST);
            String timeout =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_PROXY_TIMEOUT);
            String readResponse =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_READ_ONLY);
            String needResponse =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_NON_RESPONSE);
            String protoTypeStr =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_PROTO_TYPE);
            String taskSerialStr =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_TASK_SERIAL);
            String timeoutStr =
                EspSocketUtil.findHttpHeader(buffer, 0, headerLength, MeshCommunicationUtils.HEADER_TASK_TIMEOUT);
            
            String contentLengthStr = EspSocketUtil.findHttpHeader(buffer, 0, headerLength, HTTP.CONTENT_LEN);
            int contentLength = 0;
            if (!TextUtils.isEmpty(contentLengthStr))
            {
                contentLength = Integer.parseInt(contentLengthStr);
                EspSocketUtil.readBytes(srcSock.getInputStream(), buffer, headerLength, contentLength);
            }
            
            int protoType =
                TextUtils.isEmpty(protoTypeStr) ? EspProxyTask.M_PROTO_HTTP : Integer.parseInt(protoTypeStr);
                
            byte[] requestBytes = getRequestBytes(protoType, buffer, headerLength, contentLength);
            // String request = new String(requestBytes);
            
            MeshLog.i(DEBUG, USE_LOG4J, CLASS, "createProxyTask() bssid is: " + bssid);
            EspProxyTaskImpl task = new EspProxyTaskImpl(host, bssid, requestBytes, Integer.parseInt(timeout));
            task.setSourceSocket(srcSock);
            boolean readOnly = !TextUtils.isEmpty(readResponse) && (Integer.parseInt(readResponse) != 0);
            task.setReadOnlyTask(readOnly);
            boolean replyResponse = TextUtils.isEmpty(needResponse) || (Integer.parseInt(needResponse) == 0);
            task.setNeedReplyResponse(replyResponse);
            task.setProtoType(protoType);
            int taskSerial = TextUtils.isEmpty(taskSerialStr) ? MeshCommunicationUtils.SERIAL_NORMAL_TASK
                : Integer.parseInt(taskSerialStr);
            task.setLongSocketSerial(taskSerial);
            int taskTimeout = TextUtils.isEmpty(timeoutStr) ? 0 : Integer.parseInt(timeoutStr);
            task.setTaskTimeout(taskTimeout);
            return task;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            
            try
            {
                srcSock.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        
        return null;
    }
    
    private static byte[] getRequestBytes(int protoType, byte[] fullBuffer, int headerLength, int contentLength) {
        boolean sendContentOnly;
        switch(protoType) {
            case EspProxyTask.M_PROTO_JSON:
                sendContentOnly = true;
                break;
            case EspProxyTask.M_PROTO_NONE:
            case EspProxyTask.M_PROTO_MQTT:
            case EspProxyTask.M_PROTO_HTTP:
            default:
                sendContentOnly = false;
                break;
        }
        
        int bufferLen = sendContentOnly ? contentLength : headerLength + contentLength;
        byte[] requestBytes = new byte[bufferLen];
        for (int i = 0; i < bufferLen; ++i)
        {
            int bufferIndex = sendContentOnly ? i + headerLength : i;
            requestBytes[i] = fullBuffer[bufferIndex];
        }
        
        return requestBytes;
    }
}
