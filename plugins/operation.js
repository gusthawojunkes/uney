export default ({ app }, inject) => {
    inject('operation', getFormattedOperation);
};

const getFormattedOperation = (operation) => {
    if (operation === 'credit') return 'Crédito';
    else if (operation === 'debit') return 'Débito';
    else return 'Desconhecido';
};
