Vue.createApp({
    data() {
      return {
        clients:[],
        currentClient:[],
        cards:[],
        creditCards:[],
        debitCards:[],
      }
    },
  
    created(){
        axios.get('http://localhost:8080/api/clients/')
        .then(datos => {
            this.clients = datos.data})
        axios.get('http://localhost:8080/api/clients/current')
        .then(datos =>{
          this.currentClient = datos.data
          this.cards = this.currentClient.cards
        })
    },
  
    methods:{
      toUppercase(string){
        string = string.toUpperCase()
        return string
      },

      cortarFecha(date){
        let newDate = new Date (date)
        let month = newDate.getMonth()+1
        if(month < 10){
          month = "0" + month
        }
        let year = (newDate.getFullYear()).toString().slice(-2)
        let finalDate = month + "/" + year
        return finalDate
      },

      logOut(){
        axios.post('/api/logout')
            .then(function(response){
            window.location.href = "http://localhost:8080/web/index.html"; 
            })
    },

    disableCard(id){
      axios.patch('http://localhost:8080/api/clients/current/cards',
      `id=${id}`,
      { headers: { "content-type": "application/x-www-form-urlencoded" } }
      )
      .then(function (response) {
        window.location.href = "http://localhost:8080/web/cards.html";
      })
      .catch(function (error) {
        console.log(error);
      });
  },
  },
  
    computed:{
      separarCards(){
        this.cards.forEach(card => {
          if (card.type == 'CREDIT' && card.disable == false){
            this.creditCards.push(card)
          }
          else if (card.type == 'DEBIT' && card.disable == false) {
            this.debitCards.push(card)
          }
        });
      },

      ordenarCards(){
        this.creditCards = this.creditCards.sort((a,b)=> a.id-b.id)
        this.debitCards = this.debitCards.sort((a,b)=> a.id-b.id)
      },
    }
  }).mount('#app')