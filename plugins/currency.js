export default ({ app }, inject) => {
    inject('currency', formatCurrency);
};

const BR_CURRENCY = {
    style: 'currency',
    currency: 'BRL',
};

const formatCurrency = (value) => {
    return value.toLocaleString('pt-br', BR_CURRENCY);
};
