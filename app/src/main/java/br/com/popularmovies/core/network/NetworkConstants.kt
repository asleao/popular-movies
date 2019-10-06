package br.com.popularmovies.core.network

const val SCHEME = "https"
const val HOST = "api.themoviedb.org"
const val API_VERSION = "3/"
const val API_KEY = "api_key"

const val NETWORK_ERROR_CODE = 503
const val GENERIC_ERROR_CODE = 500
const val CODE_ERRO_NEGOCIO = 400
const val CODE_ERRO_PADRAO = 500
const val TITULO_MSG_ERRO_PADRAO = "Algo deu errado :("
const val MSG_ERRO_PADRAO = "Não foi possivel completar a operação. \nTente novamente em alguns instantes."
const val TITULO_MSG_ERRO_CONEXAO = "Ops, falha na conexão. :/"
const val MSG_ERRO_CONEXAO = "Verifique se o seu dispositivo está conectado e tente realizar esta operação novamente."
val SERVER_MSG_ERROR = "Server error ocurred.\n Please, contact the support team at some@email.com."
val ROOM_MSG_ERROR = "A error has ocurred.\n Please, contact the support team at some@email.com."
val CONNECTION_MSG_ERROR = "Problems with your connection :("
val GENERIC_MSG_ERROR_TITLE = "Something went wrong :("
val GENERIC_MSG_ERROR_MESSAGE = "Please contact the support team."