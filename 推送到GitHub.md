# 推送到GitHub指南

## 步骤1：在GitHub创建仓库

1. 访问 https://github.com/new

2. 填写仓库信息：
   - **Repository name**: `Qorder`（或你喜欢的名称）
   - **Description**: `Android备忘录App - 使用Jetpack Compose开发`
   - **Public** 或 **Private**（根据需要选择）
   - ❌ **不要勾选** "Add a README file"（我们已经有README了）
   - ❌ **不要勾选** "Add .gitignore"
   - ❌ **不要勾选** "Choose a license"

3. 点击 "Create repository"

## 步骤2：推送代码到GitHub

创建仓库后，GitHub会显示快速设置说明。复制仓库URL（类似：`https://github.com/你的用户名/Qorder.git`）

然后在项目目录执行以下命令：

```cmd
cd E:\XTest\Qorder

# 添加远程仓库（替换为你的仓库URL）
git remote add origin https://github.com/你的用户名/Qorder.git

# 推送到GitHub
git branch -M main
git push -u origin main
```

## 步骤3：验证

刷新GitHub仓库页面，应该能看到所有项目文件。

## 后续更新

当你修改代码后，可以使用以下命令推送更新：

```cmd
git add .
git commit -m "描述你的更改"
git push
```

## 常见问题

### Q: 推送时要求输入用户名和密码？
A: GitHub现在需要使用Personal Access Token：
1. 访问 https://github.com/settings/tokens
2. 点击 "Generate new token (classic)"
3. 选择权限：repo（完整仓库访问）
4. 生成token并复制
5. 推送时使用token作为密码

### Q: 如何使用SSH密钥？
A: 配置SSH密钥可以避免每次输入token：
1. 生成SSH密钥：`ssh-keygen -t ed25519 -C "你的邮箱"`
2. 添加公钥到GitHub：https://github.com/settings/keys
3. 使用SSH URL：`git@github.com:你的用户名/Qorder.git`

### Q: 推送失败怎么办？
A: 检查：
- 网络连接
- 仓库URL是否正确
- 用户名/token是否正确
- 仓库是否已存在

祝你使用愉快！
