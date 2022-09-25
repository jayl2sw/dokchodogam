<template>
  <transition name="modal">
    <div class="modal" @click.self="$emit('close')">
      <div class="modal-window">
        <div class="modal-header">
          <p @click="$emit('close')">x</p>
        </div>
        <div class="modal-content">
          <p>{{ monsterDetail.name }}</p>
          <p>타입 : {{ monsterDetail.type }}</p>
          <p>등급 : {{ monsterDetail.grade }}</p>
          <p>체력 : {{ monsterDetail.hp }}</p>
          <p>
            공격력 : {{ monsterDetail.minAttack }} ~
            {{ monsterDetail.maxAttack }}
          </p>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
// import axios from 'axios'

export default {
  props: {
    monsterDetail: Object
  }
}
</script>

<style scoped></style>
