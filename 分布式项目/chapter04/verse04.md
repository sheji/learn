# 第四章 前端工程 第四节 创建项目

## 1.初始化操作

### ①跳转到同意协议页面

#### [1]准备页面

/distribution-crowd-7-webui/src/main/resources/templates/project-1-start.html

#### [2]跳转页面

```java
urlPath = "/project/to/agree/page";
viewName = "project-1-start";

registry.addViewController(urlPath).setViewName(viewName);
```

### ②点击同意协议按钮

#### [1]将同意协议按钮放在form标签内

```html
<form action="/project/agree/protocol" method="post">
    <button type="submit" class="btn  btn-warning btn-lg">阅读并同意协议</button>
</form>
```

#### [2]将project-manager的Controller抽取接口

所在工程：distribution-crowd-1-common<br/>

接口全类名：com.atguigu.crowd.api.ProjectOperationRemoteService<br/>

```java
@FeignClient("project-manager")
public interface ProjectOperationRemoteService {
	
	@RequestMapping("/project/manager/save/whole/project")
	public ResultEntity<String> saveWholeProject(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken);

	@RequestMapping("/project/manager/save/confirm/infomation")
	public ResultEntity<String> saveConfirmInfomation(
			@RequestBody MemberConfirmInfoVO memberConfirmInfoVO);
	
	@RequestMapping("/project/manager/save/return/infromation")
	public ResultEntity<String> saveReturnInfromation(
			@RequestBody ReturnVO returnVO);
	
	@RequestMapping("/project/manager/save/project/information")
	public ResultEntity<String> saveProjectInformation(
			@RequestBody ProjectVO projectVOFront);
	
	@RequestMapping("/project/manager/save/detail/picture/path/list")
	public ResultEntity<String> saveDetailPicturePathList(@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken,
			@RequestParam("detailPicturePathList") List<String> detailPicturePathList);
	
	@RequestMapping("/project/manager/save/head/picture/path")
	public ResultEntity<String> saveHeadPicturePath(@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken,
			@RequestParam("headerPicturePath") String headerPicturePath);
	
	@RequestMapping("/project/manager/initCreation")
	public ResultEntity<ProjectVO> initCreation(
			@RequestParam("memberSignToken") String memberSignToken);
}
```

#### [3]Controller方法

所在工程：distribution-crowd-7-webui<br/>

所在类：com.atguigu.crowd.handler.ProjectHandler<br/>

```java
@RequestMapping("/project/agree/protocol")
public String agreeProtocol(HttpSession session, Model model) {
	
	// 1.检查登录状态
	MemberSignSuccessVO memberSignVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_SESSION_SIGNED_MEMBER);
	
	if(memberSignVO == null) {
		
		model.addAttribute(CrowdConstant.ATTR_NAME_REQUEST_MESSAGE, CrowdConstant.MESSAGE_LOGIN_NEEDED);
		
		return "member_login";
	}
	
	String token = memberSignVO.getToken();
	
	// 2.调用远程方法初始化项目创建
	ResultEntity<String> resultEntity = projectOperationRemoteService.initProjectCreate(token);
	
	if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
		throw new RuntimeException(resultEntity.getMessage());
	}
		
	// ※补充操作：将初始化项目的信息存入Session
	ProjectVO projectVO = resultEntity.getData();
	session.setAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT, projectVO);
	
	return "redirect:/project/to/step/one/page";
}
```

## 2.跳转到step-1页面

※以前保存项目相关数据时功能调整※

所在工程：distribution-crowd-3-database-provider<br/>

所在类：com.atguigu.crowd.service.impl.ProjectServiceImpl<br/>

所在方法：saveProject()<br/>

代码：

```java
……
// 6.保存MemberLaunchInfoPO
MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();

if(memberLauchInfoVO != null) {
	
	// ※※※※※※※※※※※补充功能※※※※※※※※※※※
	// 将旧的用户发起人信息删除
	MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();
	example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));
	memberLaunchInfoPOMapper.deleteByExample(example);
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※
	
	MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
	BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
	
	memberLaunchInfoPO.setMemberid(Integer.parseInt(memberId));
	
	memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
}
……
```



### ①准备页面

/distribution-crowd-7-webui/src/main/resources/templates/project_2_stepOne.html

### ②查询目标页面所需要的数据

webui工程→member-manager工程→根据token查询memberId(redis)→根据memberId查询MemberLaunchInfo(database)<br/>

##### [1]database-provider

接口：com.atguigu.crowd.api.DataBaseOperationRemoteService<br/>

```java
@RequestMapping("/retrieve/member/launch/info/po")
ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoPO(@RequestParam("memberId") String memberId);
```

Controller：

```java
@RequestMapping("/retrieve/member/launch/info/po")
public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoPO(@RequestParam("memberId") String memberId) {
	
	MemberLaunchInfoPO memberLaunchInfoPO = memberService.getMemberLaunchInfoPO(memberId);
	
	return ResultEntity.successWithData(memberLaunchInfoPO);
}
```

Service：

```java
@Override
public MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId) {
	
	MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();
	
	example.createCriteria().andMemberidEqualTo(memberId);
	
	List<MemberLaunchInfoPO> list = memberLaunchInfoPOMapper.selectByExample(example);
	
	if(CrowdUtils.collectionEffectiveCheck(list)) {
		return list.get(0);
	}else {
		return null;
	}
	
}
```

##### [2]member-manager

接口：com.atguigu.crowd.api.MemberManagerRemoteService<br/>

```java
@RequestMapping("/retrieve/member/launch/info/by/member/token")
public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoByMemberToken(@RequestParam("token") String token);
```

Controller：

```java
@RequestMapping("/retrieve/member/launch/info/by/member/token")
public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoByMemberToken(@RequestParam("token") String token) {
	
	// 1.根据token查询memberId
	ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(token);
	
	String memberId = resultEntity.getData();
	
	if(memberId == null) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
	}
	
	// 2.根据memberId查询MemberLaunchInfoPO
	return dataBaseOperationRemoteService.retrieveMemberLaunchInfoPO(memberId);
}
```

##### [3]webui

```java
@RequestMapping("/project/to/create/project/page")
public String toCreateProjectPage(HttpSession session, Model model) {
	
	// 1.获取当前登录的用户
	MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
	
	// 2.检查signVO为null
	if(signVO == null) {
		model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_DENIED);
		return "member-login";
	}
	
	// 3.获取token数据
	String token = signVO.getToken();
	
	// 4.根据token查询MemberLaunchInfo信息
	ResultEntity<MemberLaunchInfoPO> resultEntity = memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(token);
	
	// 5.检查结果
	String result = resultEntity.getResult();
	if(ResultEntity.FAILED.equals(result)) {
		throw new RuntimeException(resultEntity.getMessage());
	}
	
	// 6.获取查询结果
	MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();
	
	// 7.存入模型
	model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);
	
	return "project-create";
}
```



### ③页面回显已加载的数据

```html
<input th:value="${memberLauchInfoVO.descriptionSimple}" type="text" class="form-control" placeholder="一句话自我介绍，不超过40字">
<textarea class="form-control" rows="5" placeholder="向支持者详细介绍你自己或你的团队及项目背景，让支持者在最短时间内了解你，不超过160字" th:text="${memberLauchInfoVO.descriptionDetail}"></textarea>
<input th:value="${memberLauchInfoVO.phoneNum}" type="text" class="form-control" placeholder="此信息不会显示在项目页面">
<input th:value="${memberLauchInfoVO.serviceNum}" type="text" class="form-control" placeholder="此信息显示在项目页面">
```

textarea标签没有value属性，所以不能使用th:value，需要设置标签体，所以使用了th:text<br/>

如果考虑到memberLauchInfoVO有可能为null，可以进行相关判断<br/>

```HTML
${(memberLauchInfoVO==null)?'':memberLauchInfoVO.descriptionSimple}
${(memberLauchInfoVO==null)?'':memberLauchInfoVO.descriptionDetail}
${(memberLauchInfoVO==null)?'':memberLauchInfoVO.phoneNum}
${(memberLauchInfoVO==null)?'':memberLauchInfoVO.serviceNum}
```

## 3.上传项目头图

### ①点击“上传”按钮直接弹出文件选择框

```html
<button id="headPictureBtn" type="button" class="btn btn-primary btn-lg active">上传图片</button>
```

增加文件上传框

```html
<input id="headPictureInput" type="file" name="headPicture" style="display: none;" />
```

给#headPictureBtn按钮绑定单击响应函数

```javascript
$("#headPictureBtn").click(function(){
	
	// 调用click()函数而不传入回调函数，就表示对#headPictureInput单击
	$("#headPictureInput").click();
});
```

### ②选中文件后直接上传

```javascript
// 用户选择了具体要上传的文件后，文件上传框会触发值改变事件
$("#headPictureInput").change(function(){
	
	// 获取实际选中的文件数组
	var file = this.files[0];
	
	// 创建FormData()对象
	var formData = new FormData();
	
	// 封装文件数据
	formData.append("headPicture", file);
	
	// 发送ajax请求执行上传
	$.ajax({
		"url":"/project/upload/head/picture",
		"type":"post",
		"data":formData,
		"contentType":false,
		"processData":false,
		"success":function(){
			alert("上传成功！");
		}
	});
});
```

### ③上传文件到OSS

#### [1]引入依赖

```xml
<dependency>
	<groupId>com.aliyun.oss</groupId>
	<artifactId>aliyun-sdk-oss</artifactId>
	<version>2.8.3</version>
</dependency>
```

#### [2]上传文件的工具方法

所在工程：distribution-crowd-1-common<br/>

全类名：com.atguigu.crowd.util.UploadUtil<br/>

```java
/**
 * 上传单个文件到OSS
 * @param endpoint
 * @param accessKeyId
 * @param accessKeySecret
 * @param fileName
 * @param folderName
 * @param bucketName
 * @param inputStream
 */
public static void uploadSingleFile(
			String endpoint, 
			String accessKeyId, 
			String accessKeySecret, 
			String fileName,
			String folderName,
			String bucketName,
			InputStream inputStream) {
	try {
		
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
		// 存入对象的名称=目录名称+"/"+文件名
		String objectName = folderName + "/" + fileName;
		
		ossClient.putObject(bucketName, objectName, inputStream);
		
		// 关闭OSSClient。
		ossClient.shutdown();
	} catch (OSSException e) {
		e.printStackTrace();
		
		throw new RuntimeException(e.getMessage());
	} catch (ClientException e) {
		e.printStackTrace();
		
		throw new RuntimeException(e.getMessage());
	}
}
```

另外两个工具方法：

```java
/**
 * 生成文件名
 * @param originalFileName 原始文件名
 * @return
 */
public static String generateFileName(String originalFileName) {
	
	// 截取扩展名部分
	String extensibleName = "";
	
	if(originalFileName.contains(".")) {
		extensibleName = originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	
	return UUID.randomUUID().toString().replaceAll("-", "")+extensibleName;
}
```





#### [3]在yml文件中需要维护的参数

```yml
oss.project.parent.folder: project
oss.endpoint: http://oss-cn-shenzhen.aliyuncs.com
oss.accessKeyId: LTAIRyb77bOaduym
oss.accessKeySecret: oI7ziHH0vEcDT3SzPnROgzWLKXdmW6
oss.bucketName: crowd190105
oss.bucket.domain: http://crowd190105.oss-cn-shenzhen.aliyuncs.com
```

#### [4]在Controller类中引用yml中的参数

```java
@Value(value="${oss.project.parent.folder}")
private String ossProjectParentFolder;

@Value(value="${oss.endpoint}")
private String endpoint;

@Value(value="${oss.accessKeyId}")
private String accessKeyId;

@Value(value="${oss.accessKeySecret}")
private String accessKeySecret;

@Value(value="${oss.bucketName}")
private String bucketName;
	
@Value(value="${oss.bucket.domain}")
private String bucketDomain;
```

#### [5]Controller中执行文件上传

```java
@ResponseBody
@RequestMapping("/project/upload/headPicture")
public ResultEntity<String> uploadHeadPicture(@RequestParam("headPicture") MultipartFile headPicture) throws IOException {
	
	// 1.判断上传的文件是否为空
	if (!headPicture.isEmpty()) {
		
		// 2.获取文件原始文件名
		String originalFilename = headPicture.getOriginalFilename();
		
		// 3.构造存储文件的目录名
		String folderName = ossProjectParentFolder + "/" + UploadUtil.generateDayFolderName();
		
		// 4.生成文件名
		String fileName = UploadUtil.generateFileName(originalFilename);
		
		// 5.获取上传文件提供的输入流
		InputStream inputStream = headPicture.getInputStream();
		
		// 6.执行上传
		UploadUtil.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName,
				inputStream);
		
		// 7.拼装上传文件后OSS平台上访问文件的路径
		String ossAccessPath = bucketDomain+"/"+folderName+"/"+fileName;
		
		return ResultEntity.successWithData(ossAccessPath);
	}
	
	return ResultEntity.failed(CrowdConstant.MESSAGE_NOT_FILE_UPLOADED);
}
```

## 4.上传详情图片

给input type="file"设置multiple="multiple"属性就可以让文件选择框能够选择多个文件。

### ①jQuery代码

```javascript
$("#detailPictureBtn").click(function(){
	$("#detailPictureInput").click();
});
$("#detailPictureInput").change(function(){
	
	// 获取用户选择的多个文件的数组
	var files = this.files;
	
	// 创建FormData对象
	var formData = new FormData();
	
	// 将上传的文件添加到formData中
	for(var i = 0; i < files.length; i++) {
		var file = files[i];
		formData.append("detailPicture", file);
	}
	
	// 执行上传
	$.ajax({
		"url":"/project/upload/detail/picture",
		"type":"post",
		"data":formData,
		"contentType":false,
		"processData":false,
		"success":function(){
			alert("上传成功！");
		}
	});
});
```

### ②Controller代码

```java
@ResponseBody
@RequestMapping("/project/upload/detail/picture")
public ResultEntity<String> uploadDetailPicture(
			HttpSession session,
			@RequestParam("detailPicture") List<MultipartFile> detailPictureList
		) throws IOException {
	
	// 登录检查
	MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
	
	if(signVO == null) {
		
		return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
	}
	
	// 遍历用户上传的文件
	if(!CrowdUtils.collectionEffectiveCheck(detailPictureList)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
	}
	
	List<String> detailPicturePathList = new ArrayList<>();
	
	for (MultipartFile detailPicture : detailPictureList) {
		boolean empty = detailPicture.isEmpty();
		
		if(empty) {
			continue ;
		}
		
		InputStream inputStream = detailPicture.getInputStream();
		
		String originalFileName = detailPicture.getOriginalFilename();
		
		String fileName = CrowdUtils.generateFileName(originalFileName);
		
		String folderName = CrowdUtils.generateFolderNameByDate(ossProjectParentFolder);
		
		CrowdUtils.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);
		
		String detailPicturePath = bucketDomain + "/" + folderName + "/" + fileName;
		
		detailPicturePathList.add(detailPicturePath);
	}
	
	// 获取保存头图所需要的相关信息
	String memberSignToken = signVO.getToken();
	
	ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
	
	String projectTempToken = projectVO.getProjectTempToken();
	
	return projectOperationRemoteService.saveDetailPicturePathList(memberSignToken, projectTempToken, detailPicturePathList);
	
}
```



