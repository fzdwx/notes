# 头像上传微服务

>   创建项目

在service下创建service_oss

>   安装oss依赖

```xml
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.10.2</version>
</dependency>
```



## 	1.文件上传到oss

### a.定义一个application.yml

```yml
server:
  port: 8889 #当前服务端口

spring:
  profiles:
    active: dev #环境配置
  application:
    name: service-oss #服务名

aliyun:
  oss:
    endpoint: oss-cn-shenzhen.aliyuncs.com
    keyid: LTAI4G4ConDvtaYxdUy53m48
    keysecret: lBcR0w2NzFyEfaxpMd6AucjjB4EwRu
    bucketname: myacademy
```



### b.定义一个文件读取aliyun.oss

```java
@Component
@ConfigurationProperties (prefix = "aliyun.oss")
@Data
public class OssProperties {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;
}
```



### c.定义service

```java
public interface FileService {
    /**
     * 阿里云oss文件上传
     * @param is               输入流
     * @param module           文件夹名字
     * @param originalFileName 原始文件名
     *
     * @return 返回这个文件的url
     */
    String upload(InputStream is, String module, String originalFileName);
}
```



### d.实现方法

```java
@Service
public class FileServiceImpl implements FileService {
    //自动注入
    @Autowired
    OssProperties ossProperties;

    @Override
    public String upload(InputStream is, String module, String originalFileName) {
        //读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();
        //创建oss
        OSS oss = new OSSClientBuilder().build(endpoint, keyid, keysecret);
        //判断这个bucket是否存在
        if (!oss.doesBucketExist(bucketname)) {
            oss.createBucket(bucketname);//创建
            oss.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);//设置为公共读
        }
        //objectName: 文件路径 avatar/2020/08/26/default.jpg
        String folder = new DateTime().toString("yyyy/MM/dd");
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String key = module + "/" + folder + "/" + fileName + fileExtension;
        //上传文件流
        oss.putObject(bucketname, key, is);
        oss.shutdown();
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }
}
```



### e.控制层

```java
@Api(tags = "阿里云OSS文件管理")
@RestController
@RequestMapping("/admin/oss/file")
@CrossOrigin //允许跨域
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation ("文件上传")
    @PostMapping ("/upload")
    public Result upload(@ApiParam(value = "需要上传的文件",required = true) @RequestParam("file") MultipartFile file,
                         @ApiParam(value = "文件夹名字",required = true) @RequestParam ("module") String module) throws IOException {
        InputStream is = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String url = fileService.upload(is, module, originalFilename);
        return Result.ok().message("文件上传成功").data("url",url);
    }
}
```



## 2.页面实现头像上传

### a.页面模板

```vue
<el-form-item label="讲师头像">
  <el-upload
    class="avatar-uploader"
     //提交的地址
    action="http://localhost:8889/admin/oss/file/upload?module=avatar"
    :show-file-list="false"
     //成功后的回调
    :on-success="handleAvatarSuccess"
     //提交前的检测
    :before-upload="beforeAvatarUpload">
    <img v-if="teacher.avatar" :src="teacher.avatar" class="avatar">
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
</el-form-item>
```



### b.脚本

```js
methods: {
  //文件上传之前的校验
  beforeAvatarUpload(file){
    const isJPG = file.type === 'image/jpeg';
    const isLt524kb = file.size / 1024 / 1024 < 2;
    if (!isJPG) {
      this.$message.error('上传头像图片只能是 JPG 格式!');
    }
    if (!isLt524kb) {
      this.$message.error('上传头像图片大小不能超过 512kb!');
    }
    return isJPG && isLt524kb;
  },
  //文件上传成功后
  handleAvatarSuccess(response){
    this.teacher.avatar=response.data.url
    console.log(response.data.url)
    this.$forceUpdate()
  },
```



