<template>
  <Header/>
  <div>

    <el-button type="primary" @click="goToAddWork" style="margin-bottom: 20px">Add Work</el-button>

    <el-table :data="paginatedWorks" style="width: 100%">
      <el-table-column prop="createdAt" label="작성시간"></el-table-column>
      <el-table-column prop="memo" label="메모"></el-table-column>
      <el-table-column prop="workerName" label="작성자"></el-table-column>
    </el-table>

    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :total="works.length"
      @current-change="handlePageChange"
      layout="prev, pager, next">
    </el-pagination>
  </div>
</template>

<script>
import api from "@/api/api.js";
import Header from "@/components/Header.vue";

export default {
  components: {Header},
  data() {
    return {
      works: [], // 서버에서 받은 전체 워크 데이터
      currentPage: 1, // 현재 페이지 번호
      pageSize: 10, // 한 페이지에 표시할 워크 개수
      paginatedWorks: [] // 현재 페이지에 맞는 워크 데이터
    };
  },
  methods: {
    // 서버에서 워크 데이터 가져오기
    async loadWorks() {
      try {
        const response = await api.getWorkList(); // 서버에서 모든 워크 리스트를 가져옵니다.
        this.works = response.data;  // 서버에서 받은 데이터를 works 배열에 저장
        this.paginateWorks();  // 페이지네이션 함수 호출
      } catch (error) {
        console.error('Error loading works:', error);
      }
    },

    // 페이지네이션: 현재 페이지에 맞는 데이터만 잘라서 표시
    paginateWorks() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.paginatedWorks = this.works.slice(start, end);  // 현재 페이지에 맞는 워크만 가져오기
    },

    // 워크 추가 페이지로 이동
    goToAddWork() {
      this.$router.push('/add-work'); // /add-work 페이지로 이동
    },

    // 페이지 번호 변경 시 호출되는 메서드
    handlePageChange(page) {
      this.currentPage = page;  // 새로운 페이지 번호로 변경
      this.paginateWorks();  // 해당 페이지에 맞는 워크 데이터 로드
    }
  },

  // 페이지가 로드될 때 워크 데이터를 불러오기
  mounted() {
    this.loadWorks();
  }
};
</script>

<style scoped>
.add-work-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>
