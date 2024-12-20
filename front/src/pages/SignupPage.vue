<template>
  <div class="signup-container">
    <el-form :model="form" label-width="100px" @submit.native.prevent="handleSubmit">
      <el-form-item label="ID" :rules="[{ required: true, message: 'ID is required', trigger: 'blur' }]">
        <el-input v-model="form.email" placeholder="Enter your ID"></el-input>
      </el-form-item>

      <el-form-item label="성함" :rules="[{ required: true, message: 'Name is required', trigger: 'blur' }]">
        <el-input v-model="form.name" placeholder="Enter your name"></el-input>
      </el-form-item>

      <el-form-item label="Password" :rules="[{ required: true, message: 'Password is required', trigger: 'blur' }]">
        <el-input v-model="form.password" type="password" placeholder="Enter your password"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" native-type="submit" :loading="loading">가입 </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {ElMessage} from 'element-plus';
import api from "@/api/api.js";

export default {
  data() {
    return {
      form: {
        email: '',
        name: '',
        password: ''
      },
      loading: false
    };
  },
  methods: {
    async handleSubmit() {
      this.loading = true;
      if(this.form.email.length < 3 || this.form.name >5 || this.form.name < 2 || this.form.password <2){
        ElMessage.error('ID는 3자 이상, 성함은 2자 이상 5자 미만, PWD는 3자 이상이이야 합니다.')
      }


      try {
        const response = await api.signup(this.form);
        ElMessage.success('Signup successful. Please log in.');
        this.$router.push('/login'); // 회원가입 성공 후 로그인 페이지로 이동
      } catch (error) {
        this.loading = false;
        ElMessage.error('Signup failed. Please try again.');
      }
    }
  }
};
</script>

<style scoped>
.signup-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>
