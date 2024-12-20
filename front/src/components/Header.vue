<template>


  <el-header>
    <div v-if="isLoggedIn">
      <span>안녕하세요, OP님!</span>
      <el-button type="danger" @click="logout">로그아웃</el-button>
    </div>
  </el-header>
</template>


<script>
import { ref, computed } from 'vue';

export default {
  setup() {

    const isLoggedIn =!!localStorage.getItem('token');


    const logout = () => {
      localStorage.removeItem('token');
      localStorage.removeItem('name');
      window.location.href = '/login'; // 로그아웃 후 로그인 페이지로 리다이렉트
    };

    return {
      isLoggedIn,
      logout
    };
  }
};
</script>

<style scoped>
</style>

