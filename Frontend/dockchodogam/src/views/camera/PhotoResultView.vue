<template>
  <NavBar @overflow="overflow" />
  <div class="result">
    <div class="result__body">
      <div class="result__left">
        <div>
          <new-find :modelValue="results" />
          <!-- <duplicate-find :modelValue="results" />
          <undefined-find :modelValue="results" /> -->
        </div>
        <!-- 조건 걸기 / 독초몬 유,무 -->
        <!-- <div v-if="가지고 있던 독초몬 없을 때" class="dockchoMonster">
          <new-find />
        </div>
        <div v-else-if="" class="dockchoMonster">
          <duplicate-find />
        </div>
        <div v-else class="dockchoMonster">
          <undefined-find />
        </div> -->
      </div>
      <div class="result__right">
        <img src="@/assets/cat.png" alt="cat" />
        <div class="dockchoExplanation__container">
          <div class="dockchoExplanation">
            <img src="@/assets/flower_ex.png" alt="flower" />
            <h3 v-if="results.docko == true">
              독초입니다! 채집 및 섭취에 주의하세요.
            </h3>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Quod nobis
            ea quae omnis doloribus assumenda impedit, maiores id tempora
            corporis obcaecati quis minima magnam sapiente ipsa in dolores hic
            distinctio? Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Quod nobis ea quae omnis doloribus assumenda impedit, maiores id
            tempora corporis obcaecati quis minima magnam sapiente ipsa in
            dolores hic distinctio? Lorem ipsum dolor sit amet consectetur
            adipisicing elit. Quod nobis ea quae omnis doloribus assumenda
            impedit, maiores id tempora corporis obcaecati quis minima magnam
            sapiente ipsa in dolores hic distinctio? Lorem ipsum dolor sit amet
            consectetur adipisicing elit. Quod nobis ea quae omnis doloribus
            assumenda impedit, maiores id tempora corporis obcaecati quis minima
            magnam sapiente ipsa in dolores hic distinctio? Lorem ipsum dolor
            sit amet consectetur adipisicing elit. Quod nobis ea quae omnis
            doloribus assumenda impedit, maiores id tempora corporis obcaecati
            quis minima magnam sapiente ipsa in dolores hic distinctio? Lorem
            ipsum dolor sit amet consectetur adipisicing elit. Quod nobis ea
            quae omnis doloribus assumenda impedit, maiores id tempora corporis
            obcaecati quis minima magnam sapiente ipsa in dolores hic
            distinctio? Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Quod nobis ea quae omnis doloribus assumenda impedit, maiores id
            tempora corporis obcaecati quis minima magnam sapiente ipsa in
            dolores hic distinctio? Lorem ipsum dolor sit amet consectetur
            adipisicing elit. Quod nobis ea quae omnis doloribus assumenda
            impedit, maiores id tempora corporis obcaecati quis minima magnam
            sapiente ipsa in dolores hic distinctio? Lorem ipsum dolor sit amet
            consectetur adipisicing elit. Quod nobis ea quae omnis doloribus
            assumenda impedit, maiores id tempora corporis obcaecati quis minima
            magnam sapiente ipsa in dolores hic distinctio? Lorem ipsum dolor
            sit amet consectetur adipisicing elit. Quod nobis ea quae omnis
            doloribus assumenda impedit, maiores id tempora corporis obcaecati
            quis minima magnam sapiente ipsa in dolores hic distinctio? Lorem
            ipsum dolor sit amet consectetur adipisicing elit. Quod nobis ea
            quae omnis doloribus assumenda impedit, maiores id tempora corporis
            obcaecati quis minima magnam sapiente ipsa in dolores hic
            distinctio? Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Quod nobis ea quae omnis doloribus assumenda impedit, maiores id
            tempora corporis obcaecati quis minima magnam sapiente ipsa in
            dolores hic distinctio? Lorem ipsum dolor sit amet consectetur
            adipisicing elit. Quod nobis ea quae omnis doloribus assumenda
            impedit, maiores id tempora corporis obcaecati quis minima magnam
            sapiente ipsa in dolores hic distinctio? Lorem ipsum dolor sit amet
            consectetur adipisicing elit. Quod nobis ea quae omnis doloribus
            assumenda impedit, maiores id tempora corporis obcaecati quis minima
            magnam sapiente ipsa in dolores hic distinctio? Lorem ipsum dolor
            sit amet consectetur adipisicing elit. Quod nobis ea quae omnis
            doloribus assumenda impedit, maiores id tempora corporis obcaecati
            quis minima magnam sapiente ipsa in dolores hic distinctio?
          </div>
          <div class="tree_container">
            <img class="tree1" src="@/assets/tree.png" alt="tree" />
            <img class="tree2" src="@/assets/tree.png" alt="tree" />
            <img class="tree3" src="@/assets/tree2.png" alt="tree" />
          </div>
        </div>
      </div>
    </div>
    <div class="result__footer">
      <div>
        <!-- <button @click="goToCamera">다시 촬영하기</button> -->
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/main/NavBar.vue'
import NewFind from '@/components/camera/NewFind.vue'
// import DuplicateFind from '@/components/camera/DuplicateFind.vue'
// import UndefinedFind from '@/components/camera/UndefinedFind.vue'

export default {
  components: {
    NavBar,
    NewFind
    // DuplicateFind,
    // UndefinedFind
  },
  data() {
    return {
      results: this.$route.query.query
    }
  },

  methods: {
    goToCamera() {
      this.$router.push({
        path: '/camera'
      })
    }
  }
}
</script>

<style scoped>
.result {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-top: 7vh;
}
.result__body {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: stretch;
}
.result__left {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
  margin-right: 2%;
}
.result__right {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
}
.result__right > img {
  position: absolute;
  width: 120px;
  top: 10vh;
  right: 10%;
  z-index: 999;
}
.result__footer {
  display: flex;
  justify-content: center;
}
.dockchoMonster {
  border-style: groove;
  border-color: black;
  border-width: 5px;
  border-radius: 10px;
  width: 40vw;
  height: 40vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
}
.dockchoExplanation__container {
  width: 40vw;
  height: 70vh;
  /* display: flex;
  flex-direction: column; */
  align-items: center;
  /* overflow: auto; */
  background: url('@/assets/hanji.jpeg') no-repeat;
  background-size: cover;
  border-radius: 50px;
  padding: 5vh;
  text-align: left;
  position: relative;
}
.dockchoExplanation {
  height: 60vh;
  overflow: auto;
  color: #215f00;
}

.dockchoExplanation > img {
  float: left;
  width: 10vw;
  height: 60vh;
  margin-right: 1vw;
  margin-bottom: 1vh;
  object-fit: cover;
}

.tree_container img {
  height: 8vh;
  position: absolute;
  bottom: 1vh;
}
.tree_container .tree1 {
  left: -1vw;
}
.tree_container .tree2 {
  height: 5vh;
  left: 1vw;
}
.tree_container .tree3 {
  right: -1vw;
}

@media screen and (max-width: 850px) {
  .result {
    align-items: center;
    flex-direction: column;
  }
  .result__body {
    align-content: center;
    flex-direction: column;
    width: 80vw;
  }
  .result__right {
    height: auto;
    position: relative;
  }
  .result__right > img {
    position: absolute;
    top: -15vw;
    width: 20vw;
  }
  .dockchoExplanation__container {
    width: 100%;
    border-radius: 20px;
  }
  .dockchoExplanation {
    overflow-x: hidden;
    font-size: 0.8rem;
  }
  .dockchoExplanation > img {
    width: 100%;
    height: 20vh;
    margin-bottom: 3vh;
  }
  .tree_container .tree1 {
    left: -5vw;
  }
  .tree_container .tree2 {
    height: 5vh;
    left: 1vw;
  }
  .tree_container .tree3 {
    right: -3vw;
  }
}
</style>
