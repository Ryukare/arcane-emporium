import styles from './Header.module.css'

function Header() {
    return (
        <div className={styles.header}>
            <img src={"./public/logo.png"} className={styles.logo} alt={"Logo"}/>
            <h1>Arcane Emporium</h1>
        </div>
    )
}

export default Header