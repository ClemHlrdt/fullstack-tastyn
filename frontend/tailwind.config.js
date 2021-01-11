const colors = require('tailwindcss/colors')

const enablePurge = process.env.ENABLE_PURGE || false;

module.exports = {
  purge: ['./src/**/*.html', './src/**/*.ts'],
  darkMode: 'class',
  theme: {
    colors: {
      green: colors.green,
      white: colors.white,
      beige: {
        50: "#fdf3e7",
        100: "#f9dcb8",
        200: "#f5c489",
        300: "#f1ad5a",
        400: "#ed952b",
        500: "#d47c12",
        600: "#a5600e",
        700: "#76450a",
        800: "#472906",
        900: "#180e02"
      },
      gray: colors.gray,
      red: colors.red,
      green: {
        50: "#eafaf5",
        100: "#c0f1e0",
        200: "#97e8cb",
        300: "#6ddfb6",
        400: "#43d5a2",
        500: "#2abc88",
        600: "#20926a",
        700: "#17684c",
        800: "#0e3f2d",
        900: "#05150f"
      },
      pink: colors.pink,
      black: colors.black,
      purple: colors.purple,
      blue: colors.blue,
      transparent: colors.transparent
    },
    extend: {
      spacing: {
        '1/2': '50%',
        '1/3': '33.333333%',
        '2/3': '66.666666%',
        '1/4': '25%',
        '2/4': '50%',
        '3/4': '75%',
        '1/5': '20%',
        '2/5': '40%',
        '3/5': '60%',
        '4/5': '80%',
        '1/6': '16.666667%',
        '2/6': '33.333333%',
        '3/6': '50%',
        '4/6': '66.666667%',
        '5/6': '83.333333%',
        '1/12': '8.333333%',
        '2/12': '16,666667%',
        '3/12': '25%',
        '4/12': '33.333333%',
        '5/12': '41.666667%',
        '6/12': '50%',
        '7/12': '58.333333%',
        '8/12': '66.666667%',
        '9/12': '75%',
        '10/12': '83.333333%',
        '11/12': '91.666667%',
      },
      height: theme => ({
        "90": "90vh",
        "95": "95vh"
      }),
      minHeight: theme => ({
        "90": "90vh",
        "95": "95vh",
      }),
    },
    fontFamily: {
      'display': ['Bitter', 'serif'],
      'body': ['Open Sans', 'sans-serif']
    },
  },
  future: {
    
  },
  variants: {
    borderColor: ['responsive', 'hover', 'focus', 'focus-within'],
  },
  plugins: [],
}
