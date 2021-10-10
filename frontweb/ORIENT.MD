# npx create-react-app <nome-do-projeto> --template typescript
# yarn add bootstrap
# Criar arquivo sass (Customização do tem bootstrap precisa do sass):
# instalar o sass: yarn add node-sass@4.14.1
# criar pasta styles e dentro dela o arquivo custom.scss e cola:
 $theme-colors: (
  'primary': #407bff,
);

$body-bg: #e5e5e5;
$body-color: #263238;

@import '~bootstrap/scss/bootstrap.scss';
# Prettier - code formatter: personaliza a identação do código - instala a extensao
# e cria o arquivo: .prettierrc