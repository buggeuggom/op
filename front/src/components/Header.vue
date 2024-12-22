<template>

  <el-header>
    <div v-if="isLoggedIn" style="display: flex; justify-content: space-between; align-items: center;">
      <span style="font-size: 2em;">안녕하세요, OP님!</span>
      <el-button type="danger" style="margin-left: auto; font-size: 1em;" @click="logout">로그아웃</el-button>
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
      window.location.href = '/'; // 로그아웃 후 로그인 페이지로 리다이렉트
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

