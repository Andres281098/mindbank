Vue.createApp({
    data() {
      return {
        account:[],
        transactions:[],
      }
    },
  
    created(){
        const params = new URLSearchParams(window.location.search)
        const value = params.get('id')
        axios.get('http://localhost:8080/api/accounts/' + value)
        .then(datos =>{
          this.account = datos.data,
          this.transactions = datos.data.transactions})
    },
  
    methods:{
      logOut(){
        axios.post('/api/logout')
            .then(function(response){
  
            console.log('signed out!!!')
            
            window.location.href = "http://localhost:8080/web/index.html";
  
            })
    },
    },
  
    computed:{}
  }).mount('#app')