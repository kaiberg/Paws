package com.example.myapplication

data class photo(val path: String) {}

object DataManager {
    var photos: ArrayList<photo> = ArrayList()

    init{
        SeedData()
    }

/*    private fun SeedData() {
        val drawablePath: String = "drawable://"
        photos = arrayListOf(
            photo(drawablePath + R.drawable.one),
            photo(drawablePath + R.drawable.two),
            photo(drawablePath + R.drawable.three),
            photo(drawablePath + R.drawable.four),
            photo(drawablePath + R.drawable.five),
            photo(drawablePath + R.drawable.six),
            photo(drawablePath + R.drawable.seven),
            photo(drawablePath + R.drawable.eight),
        )
    }*/

    private fun SeedData() {
        photos = arrayListOf(
            photo("https://images.dog.ceo/breeds/saluki/n02091831_13361.jpg"),
            photo("https://images.dog.ceo/breeds/hound-english/n02089973_1490.jpg"),
            photo("https://images.dog.ceo/breeds/weimaraner/n02092339_6077.jpg"),
            photo("https://images.dog.ceo/breeds/australian-shepherd/forest.jpg"),
            photo("https://images.dog.ceo/breeds/shiba/shiba-5.jpg"),
            photo("https://images.dog.ceo/breeds/pointer-german/n02100236_645.jpg"),
            photo("https://images.dog.ceo/breeds/hound-walker/n02089867_3585.jpg"),
            photo("https://images.dog.ceo/breeds/hound-ibizan/n02091244_3840.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-norfolk/n02094114_1812.jpg"),
            photo("https://images.dog.ceo/breeds/leonberg/n02111129_1390.jpg"),
            photo("https://images.dog.ceo/breeds/tervuren/shadow_and_frisbee.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-kerryblue/n02093859_82.jpg"),
            photo("https://images.dog.ceo/breeds/bulldog-boston/n02096585_2082.jpg"),
            photo("https://images.dog.ceo/breeds/greyhound-italian/n02091032_372.jpg"),
            photo("https://images.dog.ceo/breeds/setter-gordon/n02101006_3450.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-fox/n02095314_1976.jpg"),
            photo("https://images.dog.ceo/breeds/hound-ibizan/n02091244_4885.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-norfolk/n02094114_1505.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-welsh/lucy.jpg"),
            photo("https://images.dog.ceo/breeds/maltese/n02085936_461.jpg"),
            photo("https://images.dog.ceo/breeds/leonberg/n02111129_2949.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-fox/n02095314_663.jpg"),
            photo("https://images.dog.ceo/breeds/coonhound/n02089078_2174.jpg"),
            photo("https://images.dog.ceo/breeds/mountain-bernese/n02107683_4018.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-bedlington/n02093647_2477.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-norwich/n02094258_2312.jpg"),
            photo("https://images.dog.ceo/breeds/retriever-chesapeake/n02099849_541.jpg"),
            photo("https://images.dog.ceo/breeds/brabancon/n02112706_1552.jpg"),
            photo("https://images.dog.ceo/breeds/cotondetulear/100_2013.jpg"),
            photo("https://images.dog.ceo/breeds/frise-bichon/jh-ezio-2.jpg"),
            photo("https://images.dog.ceo/breeds/buhund-norwegian/hakon1.jpg"),
            photo("https://images.dog.ceo/breeds/husky/n02110185_3540.jpg"),
            photo("https://images.dog.ceo/breeds/malinois/n02105162_7041.jpg"),
            photo("https://images.dog.ceo/breeds/redbone/n02090379_2463.jpg"),
            photo("https://images.dog.ceo/breeds/waterdog-spanish/20180723_185559.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-kerryblue/n02093859_2114.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-westhighland/n02098286_5902.jpg"),
            photo("https://images.dog.ceo/breeds/sharpei/noel.jpg"),
            photo("https://images.dog.ceo/breeds/corgi-cardigan/n02113186_7353.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-westhighland/n02098286_5106.jpg"),
            photo("https://images.dog.ceo/breeds/clumber/n02101556_4580.jpg"),
            photo("https://images.dog.ceo/breeds/germanshepherd/Hannah.jpg"),
            photo("https://images.dog.ceo/breeds/ridgeback-rhodesian/n02087394_3159.jpg"),
            photo("https://images.dog.ceo/breeds/hound-english/n02089973_437.jpg"),
            photo("https://images.dog.ceo/breeds/retriever-chesapeake/n02099849_1215.jpg"),
            photo("https://images.dog.ceo/breeds/maltese/n02085936_7515.jpg"),
            photo("https://images.dog.ceo/breeds/spaniel-japanese/n02085782_4436.jpg"),
            photo("https://images.dog.ceo/breeds/poodle-toy/n02113624_3796.jpg"),
            photo("https://images.dog.ceo/breeds/eskimo/n02109961_9973.jpg"),
            photo("https://images.dog.ceo/breeds/terrier-patterdale/Patterdale.jpg")
        )
    }
}