package com.changgou.goods.util;

import com.changgou.goods.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FastDFSUtil {
    //加载tracker拼接信息
    static {
        try {
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 抽取trackerServer
     * @return
     * @throws Exception
     */
    public static TrackerServer trackerServer() throws Exception{
        //通过Tracker创建客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerService服务，获取连接信息
        return trackerClient.getConnection();
    }

    /**
     * 抽取storageClient
     * @param trackerServer
     * @return
     */
    public static StorageClient storageClient(TrackerServer trackerServer){
        //通过TrackerService服务获取Storage 信息，创建StorageClient对象，并存储Storage连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }
    /**
     * 文件上传
     * @param fastDFSFile 上传文件的信息
     * @return strings 文件的访问路径 http://47.94.224.82:8888/group1/M00/00/00/rBF2E18dNPGAFvZwAAFFmIsb9qI813.jpg
     * @throws Exception
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception{
        NameValuePair[] nameValuePairs = new NameValuePair[1];
        nameValuePairs[0] = new NameValuePair("auth",fastDFSFile.getAuthor());
        //通过Tracker创建客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerService服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerService服务获取Storage 信息，创建StorageClient对象，并存储Storage连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        /**
         * 通过StorageClient访问Storage，实现文件上传，并存储文件信息
         * 参数1：上传文件的字节数组
         * 参数2:上传文件的后缀名
         * 参数3：附加参数 例如：作者：。。
         *                    地址：上海，北京
         * 返回数组：
         *      [0]：文件上传到fastDFS 的storage的group
         *      [1]：文件名：虚拟路径+文件 /M00/00/44/1.jpg
         */
        String[] strings = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), nameValuePairs);
        return strings;
    }

    /**
     * 获取文件信息
     * @param group_name 文件组名：group1
     * @param remote_filename 文件名：M00/00/00/1.jpg
     * @return
     * @throws Exception
     */
    public static FileInfo getFastDFSInfo(String group_name, String remote_filename) throws Exception {
        //通过tracker创建客户端对象trackerClient，通过TrackerClient获取trackerService的连接
        TrackerServer trackerServer = FastDFSUtil.trackerServer();
        //通过TrackerService获取storage信息，创建StorageClient对象，并存储storage信息
        StorageClient storageClient = FastDFSUtil.storageClient(trackerServer);
        FileInfo file_info = storageClient.get_file_info(group_name, remote_filename);
        return file_info;
    }

    /**
     * 文件下载
     * @param group_name 文件组名：group1
     * @param remote_filename 文件名：M00/00/00/1.jpg
     * @return
     * @throws Exception
     */
    public static InputStream downLoadFile(String group_name, String remote_filename) throws Exception{
        TrackerServer trackerServer = FastDFSUtil.trackerServer();
        StorageClient storageClient = FastDFSUtil.storageClient(trackerServer);
        byte[] bytes = storageClient.download_file(group_name, remote_filename);
        InputStream stream = new ByteArrayInputStream(bytes);
        return stream;
    }

    /**
     * 文件删除
     * @param group_name 文件组名：group1
     * @param remote_filename 文件名：M00/00/00/1.jpg
     * @throws Exception
     */
    public static void deleteFile(String group_name, String remote_filename) throws Exception {
        TrackerServer trackerServer = trackerServer();
        StorageClient storageClient = storageClient(trackerServer);
        storageClient.delete_file(group_name,remote_filename);
    }

    /**
     * 获取storage信息
     * @return
     * @throws Exception
     */
    public static int getStorageInfo() throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        return storeStorage.getStorePathIndex();
    }

    /**
     * 获取tracker信息，IP 端口号
     * http://47.94.224.82:8888/group1/M00/00/00/rBF2E18ecZaALEg0AAFFmIsb9qI212.jpg
     * @return
     */
    public static String getTrackerInfo() throws Exception {
        String ip = trackerServer().getInetSocketAddress().getHostName();
        int port = ClientGlobal.getG_tracker_http_port();
        String url = "http://"+ip+":"+port;
        return url;
    }

    public static void main(String[] args) throws Exception {
        //获取文件信息
        //FileInfo group1 = getFastDFSInfo("group1", "M00/00/00/rBF2E18ecZaALEg0AAFFmIsb9qI212.jpg");
        //System.out.println(group1.getSourceIpAddr());
        //System.out.println(group1.getFileSize());

        //文件下载
        /*InputStream is = downLoadFile("group1", "M00/00/00/rBF2E18ecZaALEg0AAFFmIsb9qI212.jpg");
        //文件输出流
        FileOutputStream os = new FileOutputStream("D:/1.jpg");
        byte[] buffer = new byte[1024];//定义缓冲区
        while(is.read(buffer)!=-1){//将InputStream写入缓冲区中，-1表示写入缓冲区完成
            os.write(buffer);//文件输入流读取缓冲区数据
        }
        os.flush();
        os.close();
        is.close();*/

        deleteFile("group1","M00/00/00/rBF2E18ecZaALEg0AAFFmIsb9qI212.jpg");
        //获取tracker信息
       // System.out.println(getTrackerInfo());
        //System.out.println(getStorageInfo());
    }
}