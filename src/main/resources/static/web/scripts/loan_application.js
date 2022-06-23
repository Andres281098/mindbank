Vue.createApp({
    data() {
      return {
        clientes: [],
        loans:[],
        mortgageLoan:[],
        autoLoan:[],
        personalLoan:[],
        loanId:"",
        loanType:0,
        loanPayments:0,
        loanAmount: 0,
        loanAccount: "",
        idCliente: 0,
        payments:[],
        mortgagePayments:[],
        personalPayments:[],
        autoPayments:[],
        personalLoan:[],
        cliente1: [],
        accounts: [],
        loanPctInterest: 0,
      };
    },
  
    created() {
      axios.get("/api/clients")
          .then((datos) => {
        this.clientes = datos.data;
      });
  
      axios.get("/api/clients/current")
          .then((datos) => {
        this.cliente1 = datos.data;
        this.accounts = this.cliente1.accounts.filter(account => account.disable == false)
          });
  
      axios.get("/api/loans")
            .then((datos)=>{
              this.loans = datos.data;
              this.mortgageLoan = this.loans.filter(loan => loan.id==1);
              this.personalLoan = this.loans.filter(loan => loan.id==2);
              this.autoLoan = this.loans.filter(loan => loan.id==3);
              this.mortgagePayments = this.mortgageLoan[0].payments
              this.autoPayments = this.autoLoan[0].payments;
              this.personalPayments = this.personalLoan[0].payments; 
            })
    },
  
    methods: {
      createLoan() {
        axios.post("/api/loans",
            {
              loanId:this.loanType,
              amount:this.loanAmount,
              payments:this.loanPayments,
              destinationAccount:this.loanAccount,
            }
          )
          .then(function (response) {
            window.location.href = "/web/accounts.html";
          })
          .catch(function (error) {
            console.log(error);
          });
      },
  
      funcionPrueba(){
    
        if(this.loanType == 1)
        {
          this.payments = this.mortgagePayments;
          this.loanPctInterest = this.mortgageLoan[0].pctInterest
        }
        else if(this.loanType == 2)
        {
          this.payments = this.personalPayments;
          this.loanPctInterest = this.personalLoan[0].pctInterest
        }
        else
        {
          this.payments = this.autoPayments;
          this.loanPctInterest = this.autoLoan[0].pctInterest
        }
  
      },

      logOut(){
        axios.post('/api/logout')
            .then(function(response){
            window.location.href = "/web/index.html";
            })
    },
    },
  
    computed: {
    },
  }).mount("#app");