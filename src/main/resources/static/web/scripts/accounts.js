Vue.createApp({
  data() {
    return {
      clients:[],
      currentClient:[],
      accounts:[],
      loans:[],
      accType:"",
    }
  },

  created(){
      axios.get('http://localhost:8080/api/clients/')
      .then(datos => {
          this.clients = datos.data})
      axios.get('http://localhost:8080/api/clients/current')
      .then(datos =>{
          this.currentClient = datos.data
          this.accounts = datos.data.accounts.sort((a1,a2) => a1.id - a2.id);
          this.accounts = this.accounts.filter(account => account.disable == false);
          this.loans = this.currentClient.loans
      })
  },

  methods:{
    cortarFecha(date){
      let newDate = new Date (date)
      let day = '0' + newDate.getDay()
      let month = ('0' + newDate.getMonth())
      let year = newDate.getFullYear()
      let finalDate = day + "/" + month + "/" + year
      return finalDate
    },

    logOut(){
      axios.post('/api/logout')
          .then(function(response){
          window.location.href = "http://localhost:8080/web/index.html";
          })
  },

  createAccount(){
    axios.post("http://localhost:8080/api/clients/current/accounts",
    `accountType=${this.accType}`,
        { headers: { "content-type": "application/x-www-form-urlencoded" } })
        .then(function (response) {
          window.location.href = "http://localhost:8080/web/accounts.html";
        })
        .catch(function (error) {
          console.log(error);
        });
  },

  deleteAccount(id){
    axios.patch('http://localhost:8080/api/clients/current/accounts',
    `id=${id}`,
    { headers: { "content-type": "application/x-www-form-urlencoded" } }
    )
    .then(function (response) {
    window.location.href = "http://localhost:8080/web/accounts.html";
    })
      .catch(function (error) {
        console.log(error);
      });
  },
},

  computed:{
  }
}).mount('#app')