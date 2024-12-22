<template>
  <div class="add-work-container">
    <el-form :model="form" label-width="100px" @submit.native.prevent="handleAddWork">
      <el-form-item label="업무 내용" type="textarea" :rules="[{ required: true, message: '업무 내용을 작성하시오', trigger: 'blur' }]">
        <el-input v-model="form.memo" placeholder="업무 내용을 작성하시오"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" native-type="submit" :loading="loading">작성</el-button>
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
        memo: ''
      },
      loading: false
    };
  },
  methods: {
    async handleAddWork() {
      this.loading = true;
      if (this.form.memo.length < 3) {
        ElMessage.error('3글자이상 작성하시오');
        this.loading = false;
      } else {

        try {
          await api.creatWork(this.form);
          ElMessage.success('작성 완료!');
          this.$router.push('/');
        } catch (error) {
          this.loading = false;
          ElMessage.error('작성 실패');
        }
      }
    }
  }
};
</script>

<style scoped>
.add-work-container {
  margin-top: auto;
  width: 70%;
  font-size: medium;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>
