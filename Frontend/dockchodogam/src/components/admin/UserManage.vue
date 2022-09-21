<template>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">username</th>
        <th scope="col">nickname</th>
        <th scope="col">created_at</th>
        <th scope="col">rank_point</th>
        <th scope="col">money</th>
        <th scope="col">fix</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(user, i) in userData" :key="i">
        <th scope="row">{{ i + 1 }}</th>
        <td>{{ user.username }}</td>
        <td>{{ user.nickname }}</td>
        <td>{{ user.created_at }}</td>
        <td>{{ user.rank_point }}</td>
        <td>{{ user.money }}</td>
        <td><button>fix</button></td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  data() {
    return {
      userData: [
        {
          username: 'ohm55545',
          nickname: '하민몬',
          created_at: '2022.09.19',
          rank_point: '100000',
          money: '100000'
        }
      ]
    }
  }
}
</script>

<style scoped></style>
