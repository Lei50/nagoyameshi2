let maxDate = new Date();
 maxDate = maxDate.setMonth(maxDate.getMonth() + 1);
 
 let minDate = new Date();
 minDate.setDate(minDate.getDate() + 1);
 
 flatpickr('#reservationDate', {
   locale: 'ja',
   minDate: minDate,
   maxDate: maxDate
 });