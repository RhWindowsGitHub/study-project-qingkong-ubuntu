<template>
  <!--Login 登陆主体表单-->
    <div style="text-align: center;margin: 0 20px">

        <div style="margin-top: 150px">
            <div style="font-size: 50px;font-weight: bold">登陆</div>
            <div style="font-size: 15px; color: grey">您好！欢迎来到本项目，请进行登陆操作</div>
        </div>

        <div style="margin-top: 50px">
            <el-input  type="text" placeholder="用户名 || 邮箱">
                <template #prefix>
                    <el-icon><User /></el-icon>
                </template>
            </el-input>
            <el-input style="margin-top: 10px" type="password" placeholder="密码">
                <template #prefix>
                    <el-icon><Lock /></el-icon>
                </template>
            </el-input>
        </div>

        <el-row style="margin-top: 5px">
            <el-col :span="12" style="text-align: left">
                <el-checkbox v-model="checked1" label="记住我"></el-checkbox>
            </el-col>
            <el-col :span="12" style="text-align: right">
                <el-link>忘记密码</el-link>
            </el-col>
        </el-row>

        <div style="margin-top: 40px">
            <el-button style="width: 270px" type="success" plain>立即登陆</el-button>
        </div>

        <el-divider>
            <span style="color: grey;font-size: 13px"></span>
        </el-divider>

        <div>
            <el-button style="width: 270px" type="warning" plain>注册账号</el-button>
        </div>
    </div>
</template>

<script setup>
import {Lock, User} from "@element-plus/icons-vue";
</script>

<style scoped>

</style>