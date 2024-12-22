<template>
  <div class="login-container">
    <Header/>
    <el-form :model="form" label-width="100px" @submit.native.prevent="handleSubmit">
      <el-form-item label="ID" :rules="[{ required: true, message: 'id를 입력하세요', trigger: 'blur' }]">
        <el-input v-model="form.email" placeholder="Enter your ID"></el-input>
      </el-form-item>

      <el-form-item label="Password" :rules="[{ required: true, message: 'Password를 입력하세요', trigger: 'blur' }]">
        <el-input v-model="form.password" type="password" placeholder="Enter your password"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" native-type="submit" :loading="loading">로그인</el-button>
        <el-button @click="goToSignupPage" type="text">회원가입</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {ElMessage} from 'element-plus';
import api from "@/api/api.js";
import Header from "@/components/Header.vue";

export default {
  components: {Header},
  data() {
    return {
      form: {
        email: '',
        password: ''
      },
      loading: false
    };
  },
  methods: {
    async handleSubmit() {
      this.loading = true;
      try {
        const response = await api.login({
          email: this.form.email,
          password: this.form.password
        });
        const token = response.data.token;
        const name = response.data.name;

        localStorage.setItem('token', token); // 로컬 스토리지에 토큰 저장
        localStorage.setItem('name', name); // 로컬 스토리지에 토큰 저장
        this.$router.push('/'); // 로그인 성공 후 메인 페이지로 이동
      } catch (error) {
        this.loading = false;
        if (error.response && error.response.status === 401) {
          ElMessage.error('Unauthorized! Please log in again.');
        } else {
          ElMessage.error('Login failed. Please try again.');
        }
      }
    },
    goToSignupPage() {
      this.$router.push('/signup');
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 50%;
  font-size: 120%;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>
